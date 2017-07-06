package de.aoe.musicworld.base.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import de.aoe.musicworld.utils.ApplicationContextProvider;
/**
 * This class implements the TaskMonitorService to monitor the executing tasks.
 * 
 * @author DavidJanicki
 *
 */
public class TaskMonitorService implements Runnable {

	private static final Log LOG = LogFactory.getLog(TaskMonitorService.class);

	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		LOG.info("Run TaskMonitorService ... ");
		/** Factory method to create singleton taskExecutor */
		ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) ApplicationContextProvider
				.getApplicationContext().getBean("taskExecutor");

		while (true) {
			/** LOG active tasks all 30 seconds */
			int totalTasks = taskExecutor.getActiveCount();
			LOG.trace("Active Tasks: " + totalTasks);
			try {
				Thread.sleep(30 * 1000L);
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
				break;
			}
			if (Thread.currentThread().isInterrupted()) {
				LOG.debug("Thread interrupted, exiting");
				return;
			}
		}

	}

}