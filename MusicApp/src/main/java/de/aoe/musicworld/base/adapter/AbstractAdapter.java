package de.aoe.musicworld.base.adapter;

import java.io.OutputStream;
import java.util.Properties;

/**
 * This class implements an abstract Adapter which can be processed by any Task. 
 * 
 * @author DavidJanicki
 *
 */
public abstract class AbstractAdapter {

	/**
	 * Factory method to create Adapter.
	 * 
	 */
	abstract protected AbstractAdapter createAdapter();

	/**
	 * method to post process the send-, transport- or adapter-job
	 * 
	 */
	abstract public void postProcess();

	/**
	 * Getter / Setter for the InputStream
	 * 			the stream to transform
	 * 
	 */
	abstract public void setOutputStream(OutputStream outputStream);

	abstract public OutputStream getOutputStream();

	/**
	 * Getter / Setter for the Properties
	 * 			the global properties of the Task
	 * 
	 */
	abstract public void setProperties(Properties properties);
	
	abstract public Properties getProperties();

}