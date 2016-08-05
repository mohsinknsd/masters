package com.masters.utilities.validator;

import java.lang.reflect.Field;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;

public class FieldValidator {	
	public static SimpleEntry<Boolean, String> validate (Object object, String ... fields) {		
		for (String s : fields) {
			try {
				Field f = object.getClass().getDeclaredField(s.trim());
				f.setAccessible(true);
				if (f != null) 
					if (f.get(object) == null || f.get(object).toString().trim().equals(""))
						return new SimpleEntry<Boolean, String>(false, f.getName() + " can not be null or empty");
			} catch (NoSuchFieldException e) {
				e.printStackTrace();			
				return new SimpleEntry<Boolean, String>(false, "An exception has been occured while parsing");
			} catch (SecurityException e) {
				e.printStackTrace();
				return new SimpleEntry<Boolean, String>(false, "An exception has been occured while parsing");
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				return new SimpleEntry<Boolean, String>(false, "An exception has been occured while parsing");
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				return new SimpleEntry<Boolean, String>(false, "An exception has been occured while parsing");
			}
		}
		return new SimpleEntry<Boolean, String>(true, "Each field has been validated");
	}
	
	public static SimpleEntry<Boolean, String> validate (HashMap<String, String> map, String ... fields) {
		for (String s : fields)
			if (map.get(s) == null || map.get(s).trim().equals(""))
				return new SimpleEntry<Boolean, String>(false, s + " can not be null or empty");
		return new SimpleEntry<Boolean, String>(true, "Each field has been validated");
	}
}