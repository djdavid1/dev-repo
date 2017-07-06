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

	@Override
	public void run() {
		LOG.info("Run TaskMonitorService ... ");
		ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) ApplicationContextProvider
				.getApplicationContext().getBean("taskExecutor");

		while (true) {
			int totalTasks = taskExecutor.getActiveCount();
			LOG.trace("Active Tasks: " + totalTasks);
			try {
				Thread.sleep(10 * 1000L);
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