package com.masters.utilities.common;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Properties;

public class Utils {
	public static String get(Properties messages, String ... params) {
		return MessageFormat.format(messages.getProperty(params[0]), 
				(Object[]) Arrays.copyOfRange(params, 1, params.length));		
	}
}
