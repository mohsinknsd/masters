package com.masters.application.controller;

import static com.masters.application.model.Constants.MESSAGE;
import static com.masters.application.model.Constants.STATUS;
import static com.masters.application.model.Constants.URL;

import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import com.masters.authorization.model.Session;
import com.masters.authorization.model.User;
import com.masters.authorization.service.RoleService;
import com.masters.authorization.service.SessionService;
import com.masters.authorization.service.UserService;
import com.masters.utilities.encryption.Base64Utils;
import com.masters.utilities.logging.Log;
import com.masters.utilities.mail.ConfirmationMailHandler;
import com.masters.utilities.validator.FieldValidator;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;


@RestController
@RequestMapping(value = "/auth")
public class AuthRestController {

	@Autowired Environment environment;
	@Autowired UserService userService;
	@Autowired RoleService roleService;
	@Autowired SessionService sessionService;
	@Autowired HttpServletRequest httpServletRequest;	

	private static final String REGISTERED 	= "0";
	private static final String ACTIVATE 	= "1";
	private static final String DEACTIVATE 	= "2";
	private static final String BLOCKED 	= "3";

	private static final String [] FIELDS = {"firstname", "lastname", 
		"role", "email", "password", "gender", "address", "city", "state", "country"};

	//Initializing GSON
	private static Gson gson; static {
		gson = new GsonBuilder().setPrettyPrinting().create();
	}

	//BUG : Default value of status in Session table is 0, do it 1
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
				Log.d(user.toString());
				if (user.getStatus() == Byte.parseByte(BLOCKED)) {
					object.addProperty(MESSAGE, "Blocked user do not have permission to be logged in");
					return new ResponseEntity<String>(gson.toJson(object), HttpStatus.OK);
				} else if (user.getStatus() == Byte.parseByte(REGISTERED)) {
					object.addProperty(MESSAGE, "Please verify your email by clicking on confirmation link sent on your email id " + user.getEmail());
					return new ResponseEntity<String>(gson.toJson(object), HttpStatus.OK);
				} else if (user.getStatus() == Byte.parseByte(DEACTIVATE)) {
					user.setStatus(Byte.parseByte(ACTIVATE));
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
				object.addProperty(MESSAGE, "User has been logged in successfully");

				JsonObject userJson = (JsonObject) gson.toJsonTree(user);
				userJson.addProperty("token", token);
				userJson.remove("password");
				object.add("user", userJson);
				return new ResponseEntity<String>(gson.toJson(object), HttpStatus.OK);											
			} else {
				object.addProperty(MESSAGE, "Either email address or password is incorrect");
				return new ResponseEntity<String>(gson.toJson(object), HttpStatus.OK);
			}
		}		
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> logout(@RequestParam String userId, @RequestParam String trace) {
		JsonObject object = new JsonObject();
		object.addProperty(STATUS, true);
		object.addProperty(MESSAGE, "Did not find any opened session for this user");
		
		Session session = sessionService.getSession(Integer.parseInt(userId), trace);		
		if (session != null) {
			sessionService.deleteSession(session);
			object.addProperty(MESSAGE, "User has been logged out successfully");
		}
		
		return new ResponseEntity<String>(gson.toJson(object), HttpStatus.OK);		
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> register(@RequestParam HashMap<String, String> map) {
		JsonObject object = new JsonObject();
		object.addProperty(STATUS, false);
		User user = new User(map);
		user.setRole(roleService.getRole(map.get("role")));
		SimpleEntry<Boolean, String> result = FieldValidator.validate(user, FIELDS);
		if (!result.getKey()) {			
			object.addProperty(MESSAGE, result.getValue());
			return new ResponseEntity<String>(gson.toJson(object), HttpStatus.OK);
		} else {
			try {
				int userId = userService.insertUser(user);				
				object.addProperty(STATUS, userId > 0);								
				String key = Base64Utils.encrypt(String.format("%0" + (10 - String.valueOf(userId).length()) 
						+ "d", 0).replace("0", String.valueOf(userId) + "-").trim()).trim();											
				String link = httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName() + ":" 
						+ httpServletRequest.getServerPort() + httpServletRequest.getContextPath() + "/" + "auth/status?sts=" 
						+ URLEncoder.encode(Base64Utils.encrypt(ACTIVATE), "UTF-8") + "&hsh=" + URLEncoder.encode(key, "UTF-8");				
				user.setUserKey(key);
				userService.updateUser(user);
				new Thread(new ConfirmationMailHandler(MailConfiguration.class, user.getEmail(),
						"Verify your email address", link, "templates/confirmation.html")).start();				
				object.addProperty(MESSAGE, user.getFirstname() + " " + user.getLastname() + " has been registered successfully with username " 
						+ user.getUsername() + ". Please click on confirmation link sent to your email id.");					
				return new ResponseEntity<String>(gson.toJson(object), HttpStatus.OK);
			} catch (Exception e) {				
				object.addProperty(MESSAGE, e instanceof MySQLIntegrityConstraintViolationException  ||
						e instanceof ConstraintViolationException ? "User is already registered with " + user.getEmail() 
								: "Unable to register user. Please try again");
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
			if (user != null && user.getStatus() != Byte.parseByte(DEACTIVATE)) {
				String key = Base64Utils.encrypt(String.format("%0" + (10 - String.valueOf(user.getUserId()).length()) 
						+ "d", 0).replace("0", String.valueOf(user.getUserId()) + "-").trim()).trim();
				String link = httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName() + ":" 
						+ httpServletRequest.getServerPort() + httpServletRequest.getContextPath() + "/" + "auth/status?sts=" 
						+ URLEncoder.encode(Base64Utils.encrypt(DEACTIVATE), "UTF-8") + "&hsh=" + URLEncoder.encode(key, "UTF-8");
				user.setUserKey(key);
				userService.updateUser(user);
				new Thread(new ConfirmationMailHandler(MailConfiguration.class, user.getEmail(), 
						"Deactivate your account", link, "templates/deactivate.html")).start();
				object.addProperty(STATUS, true);
				object.addProperty(MESSAGE, "A deactivation link has been sent to your email account " + user.getEmail());
				return new ResponseEntity<String>(gson.toJson(object), HttpStatus.OK);
			} else {
				object.addProperty(MESSAGE, "User's account has been already deactivated");
				return new ResponseEntity<String>(gson.toJson(object), HttpStatus.OK);
			}			
		}		
	}

	@RequestMapping(value = "/status", method = RequestMethod.GET)
	public ResponseEntity<Object> status(@RequestParam String sts, @RequestParam String hsh) {
		HttpHeaders httpHeaders = new HttpHeaders();		
		try {
			if (hsh != null && !hsh.trim().equals("")) {
				User user = userService.getUser(Integer.parseInt(Base64Utils.decrypt(URLDecoder.decode(hsh.trim(), "UTF-8")
						.replace(" ", "+")).split("\\-")[0].trim()));
				if (user != null && user.getUserKey() != null && user.getUserKey().equals(hsh.trim())) {					
					if (sts != null && !sts.trim().equals("")) {
						Byte status = Byte.parseByte(Base64Utils.decrypt(URLDecoder.decode(sts.trim(), "UTF-8").replace(" ", "+")));
						user.setStatus(status);
						user.setUserKey(null);
						userService.updateUser(user);
						if (String.valueOf(status).equals(DEACTIVATE) || String.valueOf(status).equals(BLOCKED))
							sessionService.deleteSessions(user.getUserId());
					}
				} else {
					Log.e("Confirmation link has been expired!");
				}
			}
			if (httpServletRequest != null)
				httpHeaders.setLocation(new URI(httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName() + ":" 
						+ httpServletRequest.getServerPort() + httpServletRequest.getContextPath() + "/"));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);		
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> update(@RequestParam HashMap<String, String> map) {
		Map<String, Boolean> updatable = new HashMap<String, Boolean>();
		updatable.put("password", false);
		updatable.put("mobile", false);
		updatable.put("address", false);
		updatable.put("city", false);
		updatable.put("state", false);
		updatable.put("country", false);

		JsonObject object = new JsonObject();
		object.addProperty(STATUS, false);
		String userId = map.get("userId");
		if (userId == null || userId.equals("") ) {
			object.addProperty(MESSAGE, "user id can not be null or empty");
			return new ResponseEntity<String>(gson.toJson(object), HttpStatus.OK);
		} else {
			User user = userService.getUser(Integer.parseInt(userId));
			if (user != null) {
				for (String key : new ArrayList<String>(updatable.keySet())) {
					if (map.keySet().contains(key)) {
						try {
							Field field = user.getClass().getDeclaredField(key.trim());							
							if (field != null && map.get(key) != null && !map.get(key).equals("")) {
								field.setAccessible(true);
								field.set(user, map.get(key));
								updatable.put(key, true);
							} else {
								updatable.remove(key);
							}
						} catch (Exception e) {
							updatable.remove(key);
						}
					} else {
						updatable.remove(key);
					}
				}
				
				if (updatable.size() > 0) {
					userService.updateUser(user);
					String params = updatable.keySet().toString();
					object.addProperty(STATUS, true);
					object.addProperty(MESSAGE, updatable.size() > 1 ? params.substring(1, params.length() - 1) + " have been updated"
							: params.substring(1, params.length() - 1) + " has been updated");
				} else {
					object.addProperty(MESSAGE, "did not find any updatable field");
				}
			} else {
				object.addProperty(MESSAGE, "Unable to find user with user id " + userId);
			}			
			return new ResponseEntity<String>(gson.toJson(object), HttpStatus.OK);
		}		
	}

	@SuppressWarnings("finally")
	@RequestMapping(value = "/image", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> image(@RequestParam String userId, @RequestParam("image") MultipartFile image) {
		JsonObject object = new JsonObject();
		object.addProperty(STATUS, false);
		try {
			User user = userService.getUser(userId);
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
			object.addProperty(MESSAGE, "Successfully updated image on the server");
		} catch (NullPointerException e) {
			e.printStackTrace();
			object.addProperty(MESSAGE, "Unable to reach to the user with userId " + userId);
		} catch (Exception e) {
			e.printStackTrace();
			object.addProperty(MESSAGE, "Unable to update image file");
		} finally {
			return new ResponseEntity<String>(gson.toJson(object), HttpStatus.OK);
		}
	}
}