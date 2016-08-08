package com.masters.application.controller;

import java.net.URLDecoder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.masters.utilities.encryption.Base64Utils;
import com.masters.utilities.logging.Log;

@Controller
@RequestMapping("/app/response")
public class ResponseController {
	
	@RequestMapping(value = "/acknowledgement",  method = RequestMethod.GET)
	public String acknowledgement(@RequestParam("message") String message, ModelMap model) {			
		try {
			String dec = Base64Utils.decrypt(message);
			Log.e(dec);			
			String decoded = URLDecoder.decode(dec, "UTF-8");
			Log.e(decoded);
			model.addAttribute("msg", decoded);
		} catch (Exception e) {
			Log.e(e);
			model.addAttribute("msg", e.getMessage());
		}
		return "acknowledgement";
	}
}