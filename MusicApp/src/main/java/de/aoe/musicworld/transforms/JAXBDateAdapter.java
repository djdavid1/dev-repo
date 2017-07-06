package de.aoe.musicworld.transforms;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class JAXBDateAdapter extends XmlAdapter<String, Date> {

	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");

	@Override
	public String marshal(final Date value) throws Exception {
		return dateFormat.format(value.getTime());
	}

	@Override
	public Date unmarshal(final String value) throws Exception {
		return dateFormat.parse(value);
	}

}
