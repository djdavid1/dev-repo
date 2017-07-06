package de.aoe.musicworld.utils;

/**
 * Defines some constants to use in the application.
 * 
 * @author DavidJanicki
 * 
 */
public class BaseConstants {

	/**
	 * Hidden Constructor.
	 */
	protected BaseConstants() {
		//empty
	}

	/**
	 * The root of all config files.
	 */
	public static final String APP_CONFIG_ROOT = "APP_CONFIG_ROOT";
    
    /**
     * The bean name of the marshaller.
     */
    public static final String MARSHALLER_BEAN_NAME = "marshaller";

    /**
     * The bean name of the marshallerFormatted.
     */
    public static final String MARSHALLER_FORMATTED_BEAN_NAME = "marshallerFormatted";

    /**
     * The bean name of the unmarshaller.
     */
    public static final String UNMARSHALLER_BEAN_NAME = "unmarshaller";

    /**
     * The bean name of the baseExecutor.
     */
    public static final String BASE_EXECUTOR_BEAN_NAME = "baseExecutor";
    
    /**
     * The bean name of the filePollerService.
     */
    public static final String FILEPOLLER_SERVICE_BEAN_NAME = "filePollerService";
    
    /**
     * The bean name of the fileAdapter.
     */
    public static final String FILEADAPTER_BEAN_NAME = "fileAdapter";
    
    /**
     * The bean name of the fileAdapter.
     */
    public static final String RECORDSTORELEASE_TASK_BEAN_NAME = "recordsToReleasesTask";
	
}
