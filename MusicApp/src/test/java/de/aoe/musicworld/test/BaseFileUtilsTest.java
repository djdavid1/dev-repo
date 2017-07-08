package de.aoe.musicworld.test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.aoe.musicworld.utils.BaseFileUtils;
import de.aoe.musicworld.utils.StringUtils;

/**
 * Tests the class @{link FileUtils}.
 * 
 * @author DavidJanicki
 * 
 */
public class BaseFileUtilsTest {
	
	/** logging identification */
	private static final String LOGID = "\n--- TEST [" + BaseFileUtilsTest.class.getSimpleName() + "] ";

	/** test files. */
	private static final String TEST_SOURCE = "./src/test/resources/";
	private static final String CONFIG_PROPERTY = TEST_SOURCE + "test_config.properties";
	private static final String TEST_FILENAME = "worldofmusic.xml";

	/**
	 * Some properties.
	 */
	private Properties properties;
	private static final String INCOMING_WORK_DIR = "incoming_work_dir";
	private static final String INCOMING_FILTER_EXTENSION = "incoming_file_filter";
	private static final String OUTGOING_WORK_DIR = "outgoing_work_dir";
	private static final String OUTGOING_FILE_PREFIX = "outgoing_file_prefix";
	private static final String OUTGOING_FILE_EXTENSION = "outgoing_file_extension";

	@Before
	public void setUp() {
		Properties properties = new Properties();
		try {
			loadProperties(properties, CONFIG_PROPERTY);
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail("Error by reading " + CONFIG_PROPERTY + " : " + e.getCause());
		}
		setProperties(properties);
		System.out.println("SetUp properties successfully");

		String incomingWorkDir = properties.getProperty(INCOMING_WORK_DIR);
		File targetFile = new File(incomingWorkDir.concat(TEST_FILENAME));
		if (!targetFile.exists()) {
			try {
				System.out.println("Copy TestFile " + targetFile.getAbsolutePath());
				BaseFileUtils.copyFile(new File(TEST_SOURCE.concat(TEST_FILENAME)), new File(incomingWorkDir));
			} catch (IOException e) {
				e.printStackTrace();
				Assert.fail("Exception thrown: " + e.getCause());
			}
		}
	}

	/**
	 * Test the readInputFiles method.
	 */
	@Test
	public void testReadInputFiles() {
		System.out.println(LOGID + "testReadInputFiles");
		List<String> fileList = null;

		String workDir = properties.getProperty(INCOMING_WORK_DIR);
		String filter = properties.getProperty(INCOMING_FILTER_EXTENSION);
		try {
			fileList = BaseFileUtils.readInputFiles(workDir, filter);
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail("IOException thrown: " + e.getCause());
		}
		System.out.println("Found files in total: " + fileList.size());
		Assert.assertFalse("fileList is not empty", fileList.isEmpty());
	}

	@Test
	public void testGenerateUniqueFileName() {
		System.out.println(LOGID + "testGenerateUniqueFileName");
		String fileName = null;
		fileName = BaseFileUtils.generateUniqueFileName("DAT", "xml");
		Assert.assertTrue(fileName.contains("DAT") && fileName.contains("xml"));
		fileName = BaseFileUtils.generateUniqueFileName("DAT", null);
		Assert.assertTrue(fileName.contains("DAT") && !fileName.contains("xml"));
		fileName = BaseFileUtils.generateUniqueFileName(null, "xml");
		Assert.assertTrue(!fileName.contains("DAT") && fileName.contains("xml"));
		fileName = BaseFileUtils.generateUniqueFileName("", "");
		Assert.assertFalse(fileName.contains("DAT") || fileName.contains("xml"));
		fileName = BaseFileUtils.generateUniqueFileName(null, null);
		Assert.assertNotNull("fileName is not null.", StringUtils.isNotNullAndNotEmpty(fileName));
	}
	
	@Test
	public void testOutputStreamToFile() {
		System.out.println(LOGID + "testOutputStreamToFile");
		
		String outgoingWorkDir = properties.getProperty(OUTGOING_WORK_DIR);
		String outgoingFilePrefix = properties.getProperty(OUTGOING_FILE_PREFIX);
		String outgoingFileExtension = properties.getProperty(OUTGOING_FILE_EXTENSION);
		
		String outputFileName = BaseFileUtils.generateUniqueFileName(outgoingFilePrefix, outgoingFileExtension);
		System.out.println("OutputFile: " + outputFileName);
				
		OutputStream outputStream = null;
		try {
			outputStream = BaseFileUtils.stringToOutputStream("TEST");
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail("IOException thrown: " + e.getCause());
		}	
		
		File targetFile = new File(outgoingWorkDir.concat(outputFileName));
		try {
			Assert.assertTrue(BaseFileUtils.outputStreamToFile(outputStream, targetFile));
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail("IOException thrown: " + e.getCause());
		}
		if(targetFile.exists())
			targetFile.delete();

	}

	/**
	 * Loads the properties from the given file into the properties object.
	 * 
	 * @param props
	 *            the properties object
	 * @param propertiesFile
	 *            the properties file
	 * @throws IOException
	 *             in case of IO exception
	 */
	private void loadProperties(Properties props, String propertiesFile) throws IOException {
		System.out.println("Loading properties from " + propertiesFile);
		props.load(new FileReader(propertiesFile));
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

}
