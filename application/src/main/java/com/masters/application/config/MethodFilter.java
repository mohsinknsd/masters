package com.masters.application.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMethod;

public class MethodFilter implements Filter {
	
	public void init(FilterConfig filterConfig) throws ServletException { }

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res; 
		if(request.getMethod().equalsIgnoreCase(RequestMethod.POST.name())){
			chain.doFilter(req, res);
		} else {
			response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "This method is not allowed by the program");
		}
	}

	public void destroy() { }
}