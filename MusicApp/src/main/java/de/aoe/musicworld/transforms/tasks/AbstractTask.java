package de.aoe.musicworld.transforms.tasks;

import java.io.InputStream;

public abstract class AbstractTask implements Runnable {
	
	abstract protected AbstractTask createTask();
	
	abstract public void setInputStream(InputStream inputStream);
	
	abstract public InputStream getInputStream();

}
