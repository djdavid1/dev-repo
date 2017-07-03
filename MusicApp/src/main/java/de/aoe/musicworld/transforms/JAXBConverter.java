package de.aoe.musicworld.transforms;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.oxm.XmlMappingException;

public interface JAXBConverter {

	public OutputStream marshal(Object object) throws XmlMappingException, IOException;

	public Object unmarshal(InputStream inputStream) throws XmlMappingException, IOException;
	
}
