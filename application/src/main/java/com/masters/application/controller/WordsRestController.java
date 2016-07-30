package com.masters.application.controller;

import static com.masters.application.model.Constants.*;

import java.util.List;

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
import com.google.gson.reflect.TypeToken;
import com.masters.application.model.Word;
import com.masters.application.service.WordService;


@RestController
@RequestMapping(value = "/apis")
public class WordsRestController {

	@Autowired
	WordService wordService;

	//Initializing GSON
	private static Gson gson; static {
		gson = new GsonBuilder().setPrettyPrinting().create();
	}

	@RequestMapping(value = "/details", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getDetails(@RequestParam(required = false) String userId, 
			@RequestParam(required = false) String token, @RequestParam(required = false) String name) {		
		
		JsonObject object = new JsonObject();
		object.addProperty(STATUS, false);
		
		//Request Validations
		if (name == null || name.trim().equals("")) {			
			object.addProperty(MESSAGE, "Name that is word's title, can't be null or empty");
			return new ResponseEntity<String>(gson.toJson(object), HttpStatus.OK);
		} else {
			Word word = wordService.findWordByName(name);			
			if (word != null) {
				JsonObject wordJson = (JsonObject) gson.toJsonTree(word);
				wordJson.addProperty(MESSAGE, "Request Granted");
				wordJson.addProperty(STATUS, true);
				return new ResponseEntity<String>(gson.toJson(wordJson), HttpStatus.OK);
			} else {
				object.addProperty(MESSAGE, "No details found for " + name);
				return new ResponseEntity<String>(gson.toJson(object), HttpStatus.OK);
			}
		}		
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> listAllWords(@RequestParam(required = false) String userId, 
			@RequestParam(required = false) String token, @RequestParam(required = false) String category) {
		JsonObject object = new JsonObject();
		object.addProperty(STATUS, false);
		if (category == null || category.trim().equals("")) {
			object.addProperty(MESSAGE, "Category can't be null or empty");
		} else {
			List<Word> words = wordService.findWordsByCategory(userId, category);
			if (!words.isEmpty()) {
				object.addProperty(STATUS, true);
				object.addProperty(MESSAGE, "Request Granted");
				object.add("words", gson.toJsonTree(words, new TypeToken<List<Word>>() {}.getType()));
			} else {
				object.addProperty(MESSAGE, "Either this service is not subscribed or the category is invalid");
			}
		}
		return new ResponseEntity<String>(gson.toJson(object), HttpStatus.OK);
	}
}