package de.aoe.musicworld.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Provides some utilities...
 * 
 * @author DavidJanicki
 * 
 */
public class StringUtils {

	/**
	 * Hidden Constructor.
	 */
	protected StringUtils() {
		//empty
	}
	

	/**
	 * Returns Date from a String value.
	 * 
	 * @param value
	 *            the value we want to format
	 * @return Date
	 * 			  the date we want to get
	 * @throws ParseException 
	 */
	public static Date stringToDate(final String value) throws ParseException {
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		if(value != null)
			return format.parse(value);
		return null;
	}
	
	/**
	 * Returns true if the value is not null and has a length greater than 0.
	 * 
	 * @param value
	 *            the value we want to check
	 * @return true if the value is not null and has a length greater than 0.
	 */
	public static boolean isNotNullAndNotEmpty(final String value) {
		return (value != null) && value.trim().length() > 0;
	}
}
