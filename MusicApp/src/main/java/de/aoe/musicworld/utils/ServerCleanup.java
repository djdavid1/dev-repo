package de.aoe.musicworld.utils;

import static de.aoe.musicworld.utils.BaseConstants.INCOMING_WORK_DIR;


import static de.aoe.musicworld.utils.BaseConstants.INCOMING_FILE_FILTER;
import static de.aoe.musicworld.utils.BaseConstants.OUTGOING_WORK_DIR;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import de.aoe.musicworld.base.services.FilePollerService;

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
		
		ThreadPoolTaskExecutor baseExecutor = (ThreadPoolTaskExecutor) ApplicationContextProvider.getApplicationContext()
				.getBean("baseExecutor");		
		
		FilePollerService recordsPollerService = new FilePollerService(incomingWorkDir, incomingFileNameFilter);
		recordsPollerService.setTaskName("recordsToReleasesTask");
		
		// TODO Adapter() -> FileAdapter(outgoingWorkDir)
		//                   RestSaturn()
//		recordsPollerService.setOutgoingAdapter("outgoingAdapter");
		
		baseExecutor.execute(recordsPollerService);
		
	}
	
}


