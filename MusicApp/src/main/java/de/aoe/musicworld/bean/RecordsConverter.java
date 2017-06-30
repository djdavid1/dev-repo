package de.aoe.musicworld.bean;

import java.io.ByteArrayInputStream;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.aoe.musicworld.pojo.Records;
import de.aoe.musicworld.utils.ApplicationContextProvider;
import de.aoe.musicworld.utils.BaseConstants;

public class RecordsConverter {

	private static final Log LOG = LogFactory.getLog(RecordsConverter.class);

	private final Marshaller marshaller; // POJOtoXML
	private final Unmarshaller unmarshaller; // XMLtoPOJO

	public RecordsConverter() {
		marshaller = (Marshaller) ApplicationContextProvider.getBean(BaseConstants.MARSHALLER_BEAN_NAME);
		unmarshaller = (Unmarshaller) ApplicationContextProvider.getBean(BaseConstants.UNMARSHALLER_BEAN_NAME);
	}

	public String marshal(Records object) {
		OutputStream stream = new ByteArrayOutputStream();
		try {
			marshaller.marshal(object, stream);
		} catch (JAXBException e) {
			LOG.error("Exception occured while marshalling", e);
		}
		return stream.toString();
	}

	public Records unmarshal(String objectAsString) {
		try {
			return (Records) unmarshaller.unmarshal(new ByteArrayInputStream(objectAsString.getBytes()));
		} catch (JAXBException e) {
			LOG.error("Exception occured while marshalling", e);
		}
		return null;
	}

}
