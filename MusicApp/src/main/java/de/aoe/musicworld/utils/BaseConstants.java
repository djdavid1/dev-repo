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
     * The spring value injection parameter for the outgoing work directory.
     */
    public static final String OUTGOING_WORK_DIR = "${outgoing_work_dir}";

    /**
     * The spring value injection parameter for the incoming file filter.
     */
    public static final String INCOMING_FILE_FILTER = "${incoming_file_filter}";

    /**
     * The spring value injection parameter for the incoming work directory.
     */
    public static final String INCOMING_WORK_DIR = "${incoming_work_dir}";
    
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

	
}
