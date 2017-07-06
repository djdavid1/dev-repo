package de.aoe.musicworld.transforms;

import java.io.ByteArrayOutputStream;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.XmlMappingException;

import de.aoe.musicworld.pojo.Records;
import de.aoe.musicworld.utils.ApplicationContextProvider;
import de.aoe.musicworld.utils.BaseConstants;

public class RecordsJAXBConverter implements JAXBConverter {

	private final Marshaller marshaller;
	private final Unmarshaller unmarshaller;

	public RecordsJAXBConverter() {
		marshaller = (Marshaller) ApplicationContextProvider.getBean(BaseConstants.MARSHALLER_BEAN_NAME);
		unmarshaller = (Unmarshaller) ApplicationContextProvider.getBean(BaseConstants.UNMARSHALLER_BEAN_NAME);
	}

	public OutputStream marshal(Object object) throws XmlMappingException, IOException {
		if (object instanceof Records) {
			OutputStream stream = new ByteArrayOutputStream();
			QName qName = new QName("", "records");
			final JAXBElement<Records> element = new JAXBElement<>(qName, Records.class, (Records) object);
			Result result = new StreamResult(stream);
			marshaller.marshal(element, result);
			return stream;
		} else {
			throw new ClassCastException("Object is not instance of " + Records.class.getName());
		}
	}

	public Object unmarshal(InputStream inputStream) throws XmlMappingException, IOException {
		StreamSource in = new StreamSource(inputStream);
		@SuppressWarnings("unchecked")
		JAXBElement<Records> object = (JAXBElement<Records>) unmarshaller.unmarshal(in);
		return object.getValue();
	}

}
