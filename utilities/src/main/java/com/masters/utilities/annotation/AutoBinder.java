package com.masters.utilities.annotation;

import java.lang.reflect.Field;
import java.util.List;

public class AutoBinder {
	public static void init(Object cls, List<?> list) {
		 for(Field field : cls.getClass().getDeclaredFields()) {
	            field.setAccessible(true);
	            AutoBind annotation = field.getAnnotation(AutoBind.class);
	            if (annotation != null) {
	            	for (Object object : list) {
	            		try {
		                	Field f = object.getClass().getDeclaredField(annotation.value());
		            		f.setAccessible(true);
		            		if (field.getName().equals(f.get(object))) field.set(cls, object);
		                } catch (NoSuchFieldException e) {
		                    e.printStackTrace();
		                } catch (IllegalAccessException e) {
		                    e.printStackTrace();
		                }	
	            	}	                
	            }
	        }
	}
}