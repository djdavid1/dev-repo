package de.aoe.musicworld.utils;

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
	 * Create a useful and short name of this instance.
	 * 
	 * de.db.comgate.utils.MyObject@123456 -> MyObject@123456
	 * 
	 * @param object
	 *            the object we get the name from
	 * @return the name of the object
	 */
	public static String getObjectName(final Object object) {

		if (object == null || object.toString() == null) {
			return null;
		}

		String name = object.toString();
		int pos = name.lastIndexOf(".");
		if (pos > -1) {
			name = name.substring(pos + 1);
		}

		return name;
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
