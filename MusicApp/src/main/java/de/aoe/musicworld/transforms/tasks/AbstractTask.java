package de.aoe.musicworld.transforms.tasks;

import java.io.InputStream;
import java.util.Properties;

import de.aoe.musicworld.base.adapter.AbstractAdapter;

/**
 * This class implements an abstract Runnable Task 
 * which can be executed by any Service. 
 * 
 * @author DavidJanicki
 */
public abstract class AbstractTask implements Runnable {
	
	/**
	 * Factory method to create Task.
	 * 
	 */
	abstract protected AbstractTask createTask();
		
	/**
	 * Getter / Setter for the InputStream
	 * 			the stream to transform
	 * 
	 */
	abstract public void setInputStream(InputStream inputStream);
	
	abstract public InputStream getInputStream();
	
	/**
	 * Getter / Setter for the Adapter
	 * 			the adapter to transport the outputStream
	 * 
	 */
	abstract public void setAdapter(AbstractAdapter adapter);
	
	abstract public AbstractAdapter getAbstractAdapter();
	
	/**
	 * Getter / Setter for the Properties
	 * 			the global properties of the Task
	 * 
	 */
	abstract public void setProperties(Properties properties);
	
	abstract public Properties getProperties();

}
