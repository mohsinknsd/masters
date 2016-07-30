package com.masters.utilities.session;

import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class SessionUtils {
	
	private static ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
	
	static HttpSession session = null; static {
		session = attrs.getRequest().getSession();
	}
	
	public static HttpSession getSession() {
		return session;
	}	
}