package com.masters.application.controller;

import static com.masters.application.model.Constants.MESSAGE;
import static com.masters.application.model.Constants.STATUS;
import static com.masters.application.model.Constants.URL;

import java.lang.reflect.Field;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.SingletonManager;
import com.cloudinary.utils.ObjectUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.masters.application.config.MailConfiguration;
import com.masters.application.mail.ConfirmationMailHandler;
import com.masters.authorization.model.Session;
import com.masters.authorization.model.Status;
import com.masters.authorization.model.User;
import com.masters.authorization.service.RoleService;
import com.masters.authorization.service.SessionService;
import com.masters.authorization.service.StatusService;
import com.masters.authorization.service.UserService;
import com.masters.utilities.annotation.AutoBind;
import com.masters.utilities.annotation.AutoBinder;
import com.masters.utilities.encryption.Base64Utils;
import com.masters.utilities.logging.Log;
import com.masters.utilities.validator.FieldValidator;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

@RestController
@RequestMapping(value = "/auth/apis")
public class AuthRestController {

	@Autowired Environment environment;
	@Autowired UserService userService;
	@Autowired RoleService roleService;	
	@Autowired StatusService statusService;
	@Autowired SessionService sessionService;
	@Autowired ApplicationContext context;
	
	@AutoBind("title") public static Status registered;
	@AutoBind("title") public static Status activated;
	@AutoBind("title") public static Status deactivated;
	@AutoBind("title") public static Status blocked;

	private static final String [] FIELDS = {"firstname", "lastname", 
			"role", "email", "password", "gender", "address", "city", "state", "country"};
		
	private static final Locale l;
	private static Gson gson; static {
		l = Locale.getDefault();
		gson = new GsonBuilder().setPrettyPrinting().create();		
	}	

	@PostConstruct
	public void loadStatus() {		
		AutoBinder.init(this, statusService.getAllStatus());
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> login(@RequestParam HashMap<String, String> map) {		
		User user = new User();
		JsonObject object = new JsonObject();
		object.addProperty(STATUS, false);
		SimpleEntry<Boolean, String> result = FieldValidator.validate(map, "key", "password", "client", "type", "trace");
		if (!result.getKey()) {			
			object.addProperty(MESSAGE, result.getValue());
			return new ResponseEntity<String>(gson.toJson(object), HttpStatus.OK);
		} else {
			user = userService.getUser(map.get("key"), map.get("password"));
			if (user != null && user.getUserId() > 0) {				
				if (user.getStatus().equals(blocked)) {					
					object.addProperty(MESSAGE, context.getMessage("login.blocked.user", new String[]{}, l));
					return new ResponseEntity<String>(gson.toJson(object), HttpStatus.OK);
				} else if (user.getStatus().equals(registered)) {					
					object.addProperty(MESSAGE, context.getMessage("login.inactive.user", new String[]{user.getEmail()}, l));
					return new ResponseEntity<String>(gson.toJson(object), HttpStatus.OK);
				} else if (user.getStatus().equals(deactivated)) {					
					user.setStatus(activated);
					userService.updateUser(user);
				}

				String token = UUID.randomUUID().toString();
				Session session = sessionService.getSession(user.getUserId(), map.get("trace"));
				if (session == null) {
					session = new Session(map);
					session.setUser(user);
				}				
				session.setToken(token);
				session.setLastUpdatedOn(new Date());
				sessionService.saveOrUpdateSession(session);

				object.addProperty(STATUS, true);				
				object.addProperty(MESSAGE, context.getMessage("login.active.user", null, l));

				JsonObject userJson = (JsonObject) gson.toJsonTree(user);
				userJson.addProperty("token", token);
				userJson.addProperty("status", user.getStatus().getTitle());
				userJson.addProperty("role", user.getRole().getAlias());
				userJson.remove("password");
				object.add("user", userJson);
				return new ResponseEntity<String>(gson.toJson(object), HttpStatus.OK);											
			} else {				
				object.addProperty(MESSAGE, context.getMessage("login.incorrect.user", null, l));
				return new ResponseEntity<String>(gson.toJson(object), HttpStatus.OK);
			}
		}		
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> logout(@RequestParam String userId, @RequestParam String trace) {
		JsonObject object = new JsonObject();
		object.addProperty(STATUS, true);
		Session session = sessionService.getSession(Integer.parseInt(userId), trace);		
		if (session != null) {
			sessionService.deleteSession(session);
			object.addProperty(MESSAGE, context.getMessage("logout.alert", null, l));
		} else {
			object.addProperty(MESSAGE, context.getMessage("session.not.found", null, l));	
		}
		return new ResponseEntity<String>(gson.toJson(object), HttpStatus.OK);		
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> register(@RequestParam HashMap<String, String> map, @RequestParam(value="image", required=false) MultipartFile image) {
		JsonObject object = new JsonObject();
		object.addProperty(STATUS, false);		
		SimpleEntry<Boolean, String> result = FieldValidator.validate(map, FIELDS);
		if (!result.getKey()) {			
			object.addProperty(MESSAGE, result.getValue());
			return new ResponseEntity<String>(gson.toJson(object), HttpStatus.OK);
		} else {
			try {
				User user = new User(map);
				user.setRole(roleService.getRole(map.get("role")));
				user.setStatus(registered);
				user.setUsername(user.getUsername() + (userService.getUserCount(user.getFirstname(), user.getLastname()) + 1));
				if (image != null) {
					Map<String, String> config = new HashMap<String, String>();
					config.put("cloud_name", environment.getRequiredProperty("cloud_name"));
					config.put("api_key", environment.getRequiredProperty("api_key"));
					config.put("api_secret", environment.getRequiredProperty("api_secret"));
					Cloudinary cloudinary = new Cloudinary(config);
					SingletonManager manager = new SingletonManager();
					manager.setCloudinary(cloudinary);
					manager.init();
					String url = String.valueOf(cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap()).get("url"));
					user.setImage(url);
				}
				int userId = userService.insertUser(user);
				object.addProperty(STATUS, userId > 0);								
				String key = Base64Utils.encrypt(String.format("%0" + (10 - String.valueOf(userId).length()) 
						+ "d", 0).replace("0", String.valueOf(userId) + "-").trim()).trim();											
				String link = environment.getRequiredProperty("application.domain") + "auth/apis/status?sts=" 
						+ URLEncoder.encode(Base64Utils.encrypt(String.valueOf(activated.getStatusId())), "UTF-8") 
						+ "&hsh=" + URLEncoder.encode(key, "UTF-8");				
				user.setUserKey(key);
				userService.updateUser(user);
				new ConfirmationMailHandler(MailConfiguration.class, user.getEmail(),link, ConfirmationMailHandler.Mail.VERIFICATION).start();
				object.addProperty(MESSAGE, context.getMessage("register.success", new String[]{user.getFirstname(), user.getUsername()}, l));				
				return new ResponseEntity<String>(gson.toJson(object), HttpStatus.OK);
			} catch (Exception e) {
				Log.e(e);
				object.addProperty(MESSAGE, e instanceof MySQLIntegrityConstraintViolationException  ||
						e instanceof ConstraintViolationException ? context.getMessage("register.already.exist", new String[]{map.get("email")}, l) 
						: context.getMessage("register.failure", null, l));
				return new ResponseEntity<String>(gson.toJson(object), HttpStatus.OK);
			}
		}
	}

	@RequestMapping(value = "/deactivate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deactivate(@RequestParam HashMap<String, String> map) throws Exception {
		JsonObject object = new JsonObject();
		object.addProperty(STATUS, false);
		SimpleEntry<Boolean, String> result = FieldValidator.validate(map,"userId");
		if (!result.getKey()) {			
			object.addProperty(MESSAGE, result.getValue());
			return new ResponseEntity<String>(gson.toJson(object), HttpStatus.OK);
		} else {
			User user = userService.getUser(Integer.parseInt(map.get("userId")));
			if (user != null && !user.getStatus().equals(deactivated.getStatusId())) {
				String key = Base64Utils.encrypt(String.format("%0" + (10 - String.valueOf(user.getUserId()).length()) 
						+ "d", 0).replace("0", String.valueOf(user.getUserId()) + "-").trim()).trim();
				String link = environment.getRequiredProperty("application.domain") + "auth/apis/status?sts=" 
						+ URLEncoder.encode(Base64Utils.encrypt(String.valueOf(deactivated.getStatusId())), "UTF-8") 
						+ "&hsh=" + URLEncoder.encode(key, "UTF-8");
				user.setUserKey(key);
				userService.updateUser(user);
				new ConfirmationMailHandler(MailConfiguration.class, user.getEmail(), link, ConfirmationMailHandler.Mail.DEACTIVATE).start();
				object.addProperty(STATUS, true);
				object.addProperty(MESSAGE, context.getMessage("deactivate.account", new String[]{ user.getEmail()}, l));
				return new ResponseEntity<String>(gson.toJson(object), HttpStatus.OK);
			} else {
				object.addProperty(MESSAGE, context.getMessage("deactivate.failed", null, l));
				return new ResponseEntity<String>(gson.toJson(object), HttpStatus.OK);
			}			
		}		
	}

	@SuppressWarnings("finally")
	@RequestMapping(value = "/status", method = RequestMethod.GET)
	public ResponseEntity<Status> status(@RequestParam String sts, @RequestParam String hsh) {		
		HttpHeaders httpHeaders = new HttpHeaders();			
		Status status = new Status(0, "undefined", "any status that does not exists in the system", "something's getting wrong");
		try {			
			if (!hsh.trim().equals("") && !sts.trim().equals("")) {
				String userId = Base64Utils.decrypt(URLDecoder.decode(hsh.trim(), "UTF-8").replace(" ", "+")).split("\\-")[0].trim();
				User user = userService.getUser(Integer.parseInt(userId));
				if (user != null && user.getUserKey() != null && user.getUserKey().equals(hsh.trim())) {
					String decrypted = Base64Utils.decrypt(URLDecoder.decode(sts.trim(), "UTF-8").replace(" ", "+"));
					status = statusService.getStatus(Integer.parseInt(decrypted));
					user.setStatus(status);
					user.setUserKey(null);
					userService.updateUser(user);					
				}
			}			
			httpHeaders.setLocation(new URI(environment.getRequiredProperty("application.domain") 
					+ "app/response/acknowledgement?message=" + URLEncoder.encode(Base64Utils.encrypt(status.getMessage()), "UTF-8")));			
		} catch (Exception e) {
			Log.e(e);
		} finally {
			return new ResponseEntity<Status>(status, httpHeaders, HttpStatus.SEE_OTHER);	
		}
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> update(@RequestParam HashMap<String, String> map) {
		Map<String, Boolean> updatable = new HashMap<String, Boolean>();
		updatable.put("firstname", false);
		updatable.put("middlename", false);
		updatable.put("lastname", false);
		updatable.put("mobile", false);
		updatable.put("address", false);
		updatable.put("city", false);
		updatable.put("state", false);
		updatable.put("country", false);		

		JsonObject object = new JsonObject();
		object.addProperty(STATUS, false);
		String userId = map.get("userId");
		User user = userService.getUser(Integer.parseInt(userId));

		for (String key : new ArrayList<String>(updatable.keySet())) {
			try {
				Field field = user.getClass().getDeclaredField(key.trim());							
				if (field != null && map.get(key) != null && !map.get(key).trim().equals("")) {
					field.setAccessible(true);
					field.set(user, map.get(key));
					updatable.put(key, true);
				} else {
					updatable.remove(key);
				}
			} catch (Exception e) {
				updatable.remove(key);
			}
		}

		if (updatable.size() > 0) {
			if (updatable.containsKey("firstname") || updatable.containsKey("lastname")) {
				user.setUsername(user.getFirstname() + user.getLastname() + 
						userService.getUserCount(user.getFirstname(), user.getLastname()));				
			}
			userService.updateUser(user);
			String params = updatable.keySet().toString();
			object.addProperty(STATUS, true);
			object.addProperty(MESSAGE, updatable.size() > 1 
					? context.getMessage("update.multiple", new String[]{params.substring(1, params.length() - 1)}, l)
					: context.getMessage("update.single", new String[]{params.substring(1, params.length() - 1)}, l));
		} else {
			object.addProperty(MESSAGE, context.getMessage("update.failed", null, l));
		}
		
		return new ResponseEntity<String>(gson.toJson(object), HttpStatus.OK);		
	}
	

	//ISSUE : Token filter not applied here
	@SuppressWarnings("finally")
	@RequestMapping(value = "/image", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> image(@RequestParam String userId, @RequestParam("image") MultipartFile image) {
		JsonObject object = new JsonObject();
		object.addProperty(STATUS, false);
		try {
			User user = userService.getUser(Integer.parseInt(userId));
			if (user == null) throw new NullPointerException("user can not be null");
			Map<String, String> config = new HashMap<String, String>();
			config.put("cloud_name", environment.getRequiredProperty("cloud_name"));
			config.put("api_key", environment.getRequiredProperty("api_key"));
			config.put("api_secret", environment.getRequiredProperty("api_secret"));
			Cloudinary cloudinary = new Cloudinary(config);
			SingletonManager manager = new SingletonManager();
			manager.setCloudinary(cloudinary);
			manager.init();
			String url = String.valueOf(cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap()).get("url"));
			user.setImage(url);
			userService.updateUser(user);
			object.addProperty(URL, url);
			object.addProperty(STATUS, true);
			object.addProperty(MESSAGE, context.getMessage("image.updated", null, l));
		} catch (NullPointerException e) {
			Log.e(e);
			object.addProperty(MESSAGE, context.getMessage("user.not.found", new String[]{ userId}, l));
		} catch (Exception e) {
			Log.e(e);
			object.addProperty(MESSAGE, context.getMessage("image.failed", null, l));
		} finally {
			return new ResponseEntity<String>(gson.toJson(object), HttpStatus.OK);
		}
	}
	

	@RequestMapping(value = "/password", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> password(@RequestParam String userId, @RequestParam String oldPassword, @RequestParam String newPassword) {		
		JsonObject object = new JsonObject();
		object.addProperty(STATUS, false);
		String encrypted = DigestUtils.md5DigestAsHex(oldPassword.getBytes());
		User user = userService.getUser(Integer.parseInt(userId));
		if (user != null && user.getPassword().equals(encrypted)) {
			user.setPassword(newPassword);
			userService.updateUser(user);
			sessionService.deleteSessions(user.getUserId());
			object.addProperty(STATUS, true);
			object.addProperty(MESSAGE, context.getMessage("password.updated", null, l));
		} else {
			object.addProperty(MESSAGE, context.getMessage("password.incorrect", null, l));
		}			
		return new ResponseEntity<String>(gson.toJson(object), HttpStatus.OK);		
	}
}