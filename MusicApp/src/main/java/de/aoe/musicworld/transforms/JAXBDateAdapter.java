package de.aoe.musicworld.transforms;

import java.text.SimpleDateFormat;

import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * XmlAdapter adapts a Java type for custom marshaling.
 * This class enable marshaling to java.util.Date
 * 
 * @author DavidJanicki
 * 
 */
public class JAXBDateAdapter extends XmlAdapter<String, Date> {

	/* Defines the date format to "yyyy.MM.dd" */
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");

	/*
	 * (non-Javadoc)
	 * @see javax.xml.bind.annotation.adapters.XmlAdapter#marshal(java.lang.Object)
	 */
	@Override
	public String marshal(final Date value) throws Exception {
		return dateFormat.format(value.getTime());
	}

	/*
	 * (non-Javadoc)
	 * @see javax.xml.bind.annotation.adapters.XmlAdapter#unmarshal(java.lang.Object)
	 */
	@Override
	public Date unmarshal(final String value) throws Exception {
		return dateFormat.parse(value);
	}

}
