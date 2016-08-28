package com.masters.application.filter;

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
import com.masters.utilities.logging.Log;

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
		
		//ISSUE: Don't allow image and status requests without token
		if (resource.contains("masters/app/response")) {
			chain.doFilter(req, res);
		} else if (resource.equals("masters/auth/apis/login")
				|| resource.equals("masters/auth/apis/status")
				|| resource.equals("masters/auth/apis/image")
				|| resource.equals("masters/auth/apis/register/user")) {
			Log.d("Approching " + resource + " without token & user id");
			chain.doFilter(req, res);
		} else {
			String token = request.getHeader("Authorization");
			String userId = req.getParameter("userId");			
			Log.d("Approching " + resource + " with token " + token + " and user id " + userId);
			if (token != null && !token.trim().equals("") && userId != null && !userId.trim().equals("")) {				
				List<Session> sessions = sessionService.getSessions(Integer.parseInt(req.getParameter("userId")));				
				for (Session session : sessions)
					if (session.getToken().equalsIgnoreCase(token)) {
						Log.e("Session Found! Adding request to the filter chain");
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