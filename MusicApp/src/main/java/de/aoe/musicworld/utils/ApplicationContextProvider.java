package de.aoe.musicworld.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * This class is initialized with the ApplicationContext, which can be retrieved
 * later from everywhere.
 * 
 * @author DavidJanicki
 */
public class ApplicationContextProvider implements ApplicationContextAware {

	/**
	 * The application context.
	 */
	private static ApplicationContext ctx;

	/**
	 * Returns the current ApplicationContext object.
	 * 
	 * @return the spring application context
	 */
	public static ApplicationContext getApplicationContext() {
		return ctx;
	}

	/**
	 * Sets the spring application context.
	 * 
	 * @param context
	 *            the spring application context
	 */
	public void setApplicationContext(ApplicationContext context) {
		ctx = context;
	}

	/**
	 * Returns the bean object with the given name.
	 * 
	 * @param name
	 *            the name of the bean
	 * @return the bean matching the given name
	 */
	public static Object getBean(String name) {
		return ctx.getBean(name);
	}
}
