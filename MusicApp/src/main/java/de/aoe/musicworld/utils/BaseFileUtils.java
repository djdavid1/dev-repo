package de.aoe.musicworld.utils;

import java.io.ByteArrayOutputStream;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.TimeZone;

import org.apache.commons.io.FileUtils;

/**
 * Provides some utilities...
 * 
 * @author DavidJanicki
 * 
 */
public class BaseFileUtils {

	/**
	 * Hidden Constructor.
	 */
	protected BaseFileUtils() {
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
		if (!StringUtils.isNotNullAndNotEmpty(workDir) || !StringUtils.isNotNullAndNotEmpty(filter))
			throw new IOException("input work directory or file extension are empty");

		String[] fileExtensions = filter.split(";");
		List<File> files = (List<File>) FileUtils.listFiles(new File(workDir), fileExtensions, true);
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
	public static boolean outputStreamToFile(OutputStream outputStream, File outputFile) throws IOException {
		if (outputStream instanceof ByteArrayOutputStream) {
			ByteArrayOutputStream baos = (ByteArrayOutputStream) outputStream;
			Files.write(Paths.get(outputFile.getAbsolutePath()), baos.toByteArray());
			return true;
		} else {
			throw new ClassCastException("outputStream is not instance of ByteArrayOutputStream");
		}
	}

	/**
	 * This function read the string from the outputStream
	 * 
	 * @param outputStream
	 *            the outputStream to read
	 * @return String content of the outputStream
	 * @throws UnsupportedEncodingException
	 */
	public static String outputStreamToString(OutputStream outputStream) throws UnsupportedEncodingException {
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
		if (StringUtils.isNotNullAndNotEmpty(prefix)) {
			builder.append(prefix).append("_");
		}
		builder.append(uniqueName);
		if (StringUtils.isNotNullAndNotEmpty(extension)) {
			builder.append(".").append(extension);
		}
		return builder.toString();
	}

	/**
	 * This function copy file from source to target destination
	 * 
	 * @param source
	 * @param destDir
	 * @throws IOException
	 */
	public static void copyFile(File source, File destDir) throws IOException {
		FileUtils.copyFileToDirectory(source, destDir);
	}

	/**
	 * This function string to ouputStream
	 * 
	 * @param content
	 * @throws IOException
	 */
	public static OutputStream stringToOutputStream(String content) throws IOException {
		if(!StringUtils.isNotNullAndNotEmpty(content))
			throw new IOException("String content is empty");
		
		OutputStream outputStream = new ByteArrayOutputStream(100);
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
		outputStreamWriter.write(content);
		outputStreamWriter.flush();
		outputStreamWriter.close();
		return outputStream;
	}

}
