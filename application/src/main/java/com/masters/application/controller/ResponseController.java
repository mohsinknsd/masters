package com.masters.application.controller;

import java.net.URLDecoder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.masters.utilities.encryption.Base64Utils;

@Controller
@RequestMapping("/response")
public class ResponseController {
	
	@RequestMapping(value = "/acknowledgement",  method = RequestMethod.GET)
	public String acknowledgement(@RequestParam("sts") String sts, ModelMap model) {		
		try {
			sts = Base64Utils.decrypt(URLDecoder.decode(sts, "UTF-8").replace(" ", "+"));			
			model.addAttribute("msg", sts.equals(AuthRestController.ACTIVATE) ? "Your account has been activated!" : 
				sts.equals(AuthRestController.DEACTIVATE) ? "Your account has been deactivated!" : "Something's getting wrong!");
		} catch (Exception e) {			
			model.addAttribute("msg", e.getMessage());
		}
		return "acknowledgement";
	}
}