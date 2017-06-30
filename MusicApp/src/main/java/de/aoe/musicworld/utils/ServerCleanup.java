package de.aoe.musicworld.utils;

import static de.aoe.musicworld.utils.BaseConstants.INCOMING_WORK_DIR;
import static de.aoe.musicworld.utils.BaseConstants.INCOMING_FILE_FILTER;
import static de.aoe.musicworld.utils.BaseConstants.OUTGOING_WORK_DIR;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;

/**
 * Provides some utilities for clean up of the server
 * when the server crashed or was shut down.
 * In this case it could that messages were not processed.
 * 
 * @author DavidJanicki
 * 
 */

public class ServerCleanup {

	private static final Log LOG = LogFactory.getLog(ServerCleanup.class);
	
	@Value(INCOMING_WORK_DIR)
	private String incomingWorkDir = "~";

	@Value(INCOMING_FILE_FILTER)
	private String incomingFileNameFilter = "xml";

	@Value(OUTGOING_WORK_DIR)
	private String outgoingWorkDir = "~";
	
	public void cleanupOnStart() {
		LOG.debug("INCOMING_WORK_DIR=" + incomingWorkDir);
		LOG.debug("INCOMING_FILE_FILTER=" + incomingFileNameFilter);
		LOG.debug("OUTGOING_WORK_DIR=" + outgoingWorkDir);
		
		String[] fileExtensions = incomingFileNameFilter.split(";");
		List<String> listFilenames = new ArrayList<>();
		// read the fileNames in the inputfile directory, which were not processed
		try {
			listFilenames = readInputFiles(incomingWorkDir, fileExtensions);
		} catch (IOException e) {
			LOG.error(e);
		}
		for (String fileName : listFilenames) {
			LOG.debug("Found file: " + fileName);
			
		}

	}
	
	private List<String> readInputFiles(String incomingWorkDir, String[] fileExtensions) throws IOException{
		File fileDir = new File(incomingWorkDir);
		List<File> files = (List<File>) FileUtils.listFiles(fileDir, fileExtensions, true);
		List<String> listFilenames = new ArrayList<>();
		for (File file : files) {
			listFilenames.add(file.getCanonicalPath());
			LOG.debug("Found file: " + file.getCanonicalPath());
			
		}
		return listFilenames;
	}
		

}


