package de.aoe.musicworld.servlet;

import javax.servlet.ServletContextEvent;

import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import de.aoe.musicworld.base.services.TaskMonitorService;
import de.aoe.musicworld.utils.ApplicationContextProvider;

/**
 * This class is used to register/initialize some important services during
 * startup phase.
 * 
 * @author DavidJanicki
 * 
 */
public class BaseWebAppListener implements ServletContextListener {

	private static final Log LOG = LogFactory.getLog(BaseWebAppListener.class);

	/**
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet
	 *      .ServletContextEvent)
	 * @param event
	 *            The ServletContextEvent containing the ServletContext that is
	 *            being initialized
	 */
	public void contextInitialized(ServletContextEvent event) {
		LOG.info("Starting WebApp ...");
		/**
		 * Factory method to create singleton ThreadPoolTaskExecutor for base
		 * scope="singleton" @see spring applicationContext.xml
		 */
		ThreadPoolTaskExecutor baseExecutor = (ThreadPoolTaskExecutor) ApplicationContextProvider
				.getApplicationContext().getBean("baseExecutor");
		/**
		 * Add runnable TaskMonitorService to monitor tasks from taskExecutor
		 */
		baseExecutor.execute(new TaskMonitorService());
		cleanupServer();
		LOG.info("Started WebApp");
	}

	/**
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.
	 *      ServletContextEvent)
	 * @param event
	 *            The ServletContextEvent containing the ServletContext that is
	 *            being initialized
	 */
	public void contextDestroyed(ServletContextEvent event) {
		LOG.info("Shutdown WebApp ...");
		/**
		 * method to access on singleton ThreadPoolTaskExecutor for base
		 * services waitForTasksToCompleteOnShutdown = false
		 */
		ThreadPoolTaskExecutor baseExecutor = (ThreadPoolTaskExecutor) ApplicationContextProvider
				.getApplicationContext().getBean("baseExecutor");
		baseExecutor.shutdown();
		/**
		 * method to access on singleton ThreadPoolTaskExecutor for tasks
		 * waitForTasksToCompleteOnShutdown = true
		 */
		ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) ApplicationContextProvider
				.getApplicationContext().getBean("taskExecutor");
		int totalTasks = taskExecutor.getActiveCount();
		LOG.debug("Active Tasks : " + totalTasks);
		taskExecutor.shutdown();
		LOG.info("Shutdowned WebApp");
	}

	/**
	 * Clean up of the server on startup.
	 */
	private void cleanupServer() {
		try {
			/**
			 * Factory method to create ServerCleanup for starting up server
			 */
			final ServerCleanup serverCleanup = (ServerCleanup) ApplicationContextProvider.getApplicationContext()
					.getBean("serverCleanup");
			serverCleanup.cleanupOnStart();
		} catch (Exception e) {
			LOG.error("Error on startup", e);
		}
	}

}
