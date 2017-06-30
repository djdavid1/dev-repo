package de.aoe.musicworld.bean;

import java.io.ByteArrayInputStream;


import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.aoe.musicworld.pojo.Releases;
import de.aoe.musicworld.utils.ApplicationContextProvider;
import de.aoe.musicworld.utils.BaseConstants;

public class ReleasesConverter {

	private static final Log LOG = LogFactory.getLog(ReleasesConverter.class);

	private final Marshaller marshaller; // POJOtoXML
	private final Unmarshaller unmarshaller; // XMLtoPOJO

	public ReleasesConverter() {
		marshaller = (Marshaller) ApplicationContextProvider.getBean(BaseConstants.MARSHALLER_BEAN_NAME);
		unmarshaller = (Unmarshaller) ApplicationContextProvider.getBean(BaseConstants.UNMARSHALLER_BEAN_NAME);
	}

	public String marshal(Releases object) {
		OutputStream stream = new ByteArrayOutputStream();
		try {
			marshaller.marshal(object, stream);
		} catch (JAXBException e) {
			LOG.error("Exception occured while marshalling", e);
		}
		return stream.toString();
	}

	public Releases unmarshal(String objectAsString) {
		try {
			return (Releases) unmarshaller.unmarshal(new ByteArrayInputStream(objectAsString.getBytes()));
		} catch (JAXBException e) {
			LOG.error("Exception occured while marshalling", e);
		}
		return null;
	}

}
