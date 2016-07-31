package com.masters.application.config;

import javax.servlet.Filter;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AppSpringInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	@Override
    protected Class<?>[] getRootConfigClasses() {		
        return new Class[] { AppSpringConfiguration.class };
    }
  
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }
  
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }
    
    /*@Override
    protected Filter[] getServletFilters() {
        Filter [] singleton = { new TokenFilter(), new MethodFilter()};
        return singleton;
    }*/
}