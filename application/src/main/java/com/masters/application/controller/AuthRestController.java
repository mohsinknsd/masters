package com.masters.application.controller;

import static com.masters.authorization.model.Constants.MESSAGE;
import static com.masters.authorization.model.Constants.STATUS;

import java.util.HashMap;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.masters.authorization.model.User;
import com.masters.authorization.service.RoleService;
import com.masters.authorization.service.UserService;
import com.masters.utilities.session.SessionUtils;

@RestController
@RequestMapping(value = "/auth")
public class AuthRestController {	
	
	@Autowired
	UserService userService;
	
	@Autowired
	RoleService roleService;
	
	//Initializing GSON
	private static Gson gson; static {
		gson = new GsonBuilder().setPrettyPrinting().create();
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> login(@RequestParam(required = false) String email, @RequestParam(required = false) String password) {
		User user = new User();
		JsonObject object = new JsonObject();
		object.addProperty(STATUS, false);	
		if (email == null){
			object.addProperty(MESSAGE, "Email address is required to be logged in");
		} else if (password == null){
			object.addProperty(MESSAGE, "Password is required to be logged in");
		} else if (password.trim().equals("")){
			object.addProperty(MESSAGE, "Password can not be empty");
		} else {
			user = userService.getUser(email, password);
			if (user != null && user.getUserId() > 0) {
				String token = UUID.randomUUID().toString();
				object = gson.toJsonTree(user).getAsJsonObject();
				SessionUtils.getSession().setAttribute(String.valueOf(user.getUserId()), token);
				JsonObject userJson = (JsonObject) gson.toJsonTree(user);
				userJson.addProperty("token", token);
				//userJson.addProperty("role", user.getRole().getTitle());
				userJson.remove("password");
				return new ResponseEntity<String>(gson.toJson(userJson), HttpStatus.OK);
			} else {
				object.addProperty(MESSAGE, "Either email address or password is incorrect");				
			}
		}	
		return new ResponseEntity<String>(gson.toJson(object), HttpStatus.OK);
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> register(@RequestParam(required = false) HashMap<String, String> map) {				
		User user = new User(map);
		user.setRole(roleService.getRole(map.get("role")));
		System.out.println(user.toString());
		
		JsonObject object = new JsonObject();
		object.addProperty(STATUS, false);
		if (user.getEmail() == null) {			
			object.addProperty(MESSAGE, "Invalid email address");
			return new ResponseEntity<String>(gson.toJson(object), HttpStatus.OK);
		} else {
			boolean isRegistered = userService.insertUser(user);			
			object.addProperty(STATUS, isRegistered);
			object.addProperty(MESSAGE, isRegistered 
					? user.getFirstname() + " " + user.getLastname() + " has been registered successfully"
					: "Unable to register new user. Either this user is already registered or some of values should be unique like email");			
			return new ResponseEntity<String>(gson.toJson(object), HttpStatus.OK);
			
			
		}
	}
}