package com.masters.application.config;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.masters.authorization.model.Session;
import com.masters.authorization.service.SessionService;

public class TokenFilter implements Filter {

	@Autowired
	SessionService sessionService;
	
	public void destroy() { }

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "x-requested-with");

		HttpServletRequest request = (HttpServletRequest) req;
		String resource = request.getRequestURI().substring(1);
		System.out.println(resource);
		
		//If request is for token or web application then allow otherwise check token for apis in else block
		if (resource.equals("masters/auth/login") 
				|| resource.equals("masters/auth/register")				
				|| resource.equals("masters/app")) {
			chain.doFilter(req, res);        	
		} else {
			String token = request.getHeader("Authorization");			
			String userId = req.getParameter("userId");
			if (token != null && !token.equals("") && userId != null && !userId.equals("")) {
				List<Session> sessions = sessionService.getSessions(Integer.parseInt(req.getParameter("userId")));				
				for (Session session : sessions)
					if (session.getToken().equalsIgnoreCase(token)) {
						chain.doFilter(req, res);
						return;
					}
			} 
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You are not authorized for the request!");	
						
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		 SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, filterConfig.getServletContext());
	}
}