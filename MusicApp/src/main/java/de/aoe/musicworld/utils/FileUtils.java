package de.aoe.musicworld.utils;

import java.io.ByteArrayOutputStream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.TimeZone;

/**
 * Provides some utilities...
 * 
 * @author DavidJanicki
 * 
 */
public class FileUtils {

	/**
	 * Hidden Constructor.
	 */
	protected FileUtils() {
		// empty
	}

	/**
	 * This function read the fileNames from an incoming file directory
	 * 
	 * @param workDir
	 *            the incoming work directory
	 * @param filter
	 *            the filter after that we select the files
	 * @return List<String> the list of all filenames in incoming file directory
	 */
	public static List<String> readInputFiles(String workDir, String filter) throws IOException {
		String[] fileExtensions = filter.split(";");
		List<File> files = (List<File>) org.apache.commons.io.FileUtils.listFiles(new File(workDir), fileExtensions,
				true);
		List<String> listFilenames = new ArrayList<>();
		for (File file : files) {
			listFilenames.add(file.getCanonicalPath());
		}
		return listFilenames;
	}

	/**
	 * This function read file content to string
	 * 
	 * @param inputFileName
	 *            the name of the file
	 * @return String the content of the file
	 * @exception FileNotFoundException
	 */
	public static String fileToString(String inputFileName) throws FileNotFoundException {
		Scanner scanner = null;
		String content = null;
		try {
			File file = new File(inputFileName);
			scanner = new Scanner(file);
			content = scanner.useDelimiter("\\Z").next();
		} catch (FileNotFoundException e) {
			throw e;
		} finally {
			if (scanner != null)
				scanner.close();
		}
		return content;
	}

	/**
	 * This function writes content to file
	 * 
	 * @param outputFilename
	 *            the name of the file
	 * @param outputString
	 *            the content to write
	 * @exception IOException
	 */
	public static void stringToFile(String outputFilename, String outputString) throws IOException {
		Files.write(Paths.get(outputFilename), outputString.getBytes());
	}

	/**
	 * This function writes the outputStream to file
	 * 
	 * @param outputStream
	 *            the outputStream to write to the outputFile
	 * @param outputFile
	 *            the target file
	 * @exception IOException
	 */
	public static void outputStreamToFile(OutputStream outputStream, File outputFile) throws IOException {
		ByteArrayOutputStream baos = (ByteArrayOutputStream) outputStream;
		Files.write(Paths.get(outputFile.getAbsolutePath()), baos.toByteArray());
	}

	/**
	 * This function read the string from the outputStream
	 * 
	 * @param outputStream
	 *            the outputStream to read
	 * @return String content of the outputStream
	 * @throws UnsupportedEncodingException 
	 */
	public static String outputStreamToFile(OutputStream outputStream) throws UnsupportedEncodingException {
		ByteArrayOutputStream baos = (ByteArrayOutputStream) outputStream;
		if (baos != null)
			return baos.toString("UTF-8");
		return null;
	}

	/**
	 * This function generates a unique filename
	 * {prefix}_yyyyMMddHHmmssSSS.{extension}
	 * 
	 * @param prefix
	 *            the {prefix} of the filename
	 * @param extension
	 *            the {extension} of the filename
	 * @return String the generate filename
	 */
	public static String generateUniqueFileName(String prefix, String extension) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		String uniqueName = dateFormat.format(new Date());
		StringBuilder builder = new StringBuilder();
		builder.append(prefix).append("_").append(uniqueName).append(".").append(extension);
		return builder.toString();
	}

}
