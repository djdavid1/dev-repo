package de.aoe.musicworld.servlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import de.aoe.musicworld.base.services.FilePollerService;
import de.aoe.musicworld.utils.ApplicationContextProvider;
import de.aoe.musicworld.utils.BaseConstants;

/**
 * Provides some utilities for clean up of the server.
 * 
 * @author DavidJanicki
 * 
 */
public class ServerCleanup {

	private static final Log LOG = LogFactory.getLog(ServerCleanup.class);

	/**
	 * method to clean up on server boot
	 * 
	 */
	public void cleanupOnStart() {
		LOG.info("Clean up the server ...");
		
		/** Factory method to create FilePollerService */
		FilePollerService filePollerService = (FilePollerService) ApplicationContextProvider.getApplicationContext()
				.getBean(BaseConstants.FILEPOLLER_SERVICE_BEAN_NAME);
		/** The taskName defines task to transform the inputStream of file. */
		filePollerService.setTaskName(BaseConstants.RECORDSTORELEASE_TASK_BEAN_NAME);
		/** The adapterName defines adapter to post process the outputStream. */
		filePollerService.setAdapterName(BaseConstants.FILEADAPTER_BEAN_NAME);

		/** Factory method to create ThreadPoolTaskExecutor to execute the filePollerService */
		ThreadPoolTaskExecutor baseExecutor = (ThreadPoolTaskExecutor) ApplicationContextProvider
				.getApplicationContext().getBean(BaseConstants.BASE_EXECUTOR_BEAN_NAME);
		baseExecutor.execute(filePollerService);
		
		LOG.info("Started FilePollerService");
		LOG.info("Applied Transform Task : "   + filePollerService.getTaskName());
		LOG.info("Applied Outgoing Adapter : " + filePollerService.getAdapterName());
	}

}
