package de.aoe.musicworld.test;

import java.text.ParseException;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import de.aoe.musicworld.utils.StringUtils;

/**
 * Tests the class @{link FileUtils}.
 * 
 * @author DavidJanicki
 * 
 */
public class StringUtilsTest {
	
	/** logging identification */
	private static final String LOGID = "\n--- TEST [" + StringUtilsTest.class.getSimpleName() + "] ";

	/**
	 * Test the getObjectName method.
	 */
	@Test
	public void testStringToDate() {
		System.out.println(LOGID + "testStringToDate");
		
		Date dateObject = null;
		try {
			dateObject = StringUtils.stringToDate(null);
		} catch (ParseException e) {
			e.printStackTrace();
			Assert.fail("ParseException thrown: " + e.getCause());
		}
		try {
			dateObject = StringUtils.stringToDate("");
		} catch (ParseException e) {
			Assert.fail("ParseException thrown: " + e.getCause());
		}
		try {
			dateObject = StringUtils.stringToDate("01/01/2011");
		} catch (ParseException e) {
			e.printStackTrace();
			Assert.fail("ParseException thrown: " + e.getCause());
		}
		Assert.assertNotNull("DateObject is not null.", isNull(dateObject));
	}

	/**
	 * Test the isNotNullOrEmpty method.
	 */
	@Test
	public void testIsNotNullOrEmpty() {
		System.out.println(LOGID + "testIsNotNullOrEmpty");
		
		Assert.assertFalse("<null> seems not to be empty.", StringUtils.isNotNullAndNotEmpty(null));
		Assert.assertFalse("The value '' seems not to be empty.", StringUtils.isNotNullAndNotEmpty(""));
		Assert.assertTrue("The value 's' seems to be empty.", StringUtils.isNotNullAndNotEmpty("s"));
	}

	private boolean isNull(Object obj) {
		return obj == null;
	}

}
