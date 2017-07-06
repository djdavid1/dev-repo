package de.aoe.musicworld.transforms;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.oxm.XmlMappingException;

/**
 * This interfaces describes common JAXB unmarshal/marshal functionalities
 * 
 * @author DavidJanicki
 * 
 */
public interface JAXBConverter {

	/**
	 * marshal any Object to OutputStream
	 * 
	 * @param object
	 * @return OutputStream
	 */
	public OutputStream marshal(Object object) throws XmlMappingException, IOException;

	/**
	 * Unmarshal any InputStream to Object
	 * 
	 * @param inputStream
	 * @return Object
	 */
	public Object unmarshal(InputStream inputStream) throws XmlMappingException, IOException;

}
