package de.aoe.musicworld.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

	public static List<String> readInputFiles(String incomingWorkDir, String[] fileExtensions) throws IOException {
		File fileDir = new File(incomingWorkDir);
		List<File> files = (List<File>) org.apache.commons.io.FileUtils.listFiles(fileDir, fileExtensions, true);
		List<String> listFilenames = new ArrayList<>();
		for (File file : files) {
			listFilenames.add(file.getCanonicalPath());
		}
		return listFilenames;
	}

	public static String fileToString(String fileName) throws FileNotFoundException {
		File file = new File(fileName);
		// TODO check if file exists

		Scanner scanner = null;
		String content = null;
		try {
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

	public static void stringToFile(String outputFilename, String outputString) throws IOException {
		// TODO check if output directory exists
		
		Files.write(Paths.get(outputFilename), outputString.getBytes());
	}

}
