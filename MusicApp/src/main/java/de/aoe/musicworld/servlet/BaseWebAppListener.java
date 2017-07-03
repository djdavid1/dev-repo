package de.aoe.musicworld.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import de.aoe.musicworld.base.services.FilePollerService;
import de.aoe.musicworld.base.services.TaskMonitorService;
import de.aoe.musicworld.utils.ApplicationContextProvider;
import de.aoe.musicworld.utils.ServerCleanup;

/**
 * This class is used to register/initialize some important services during
 * startup phase.
 * 
 * @author DavidJanicki
 * 
 */
public class BaseWebAppListener implements ServletContextListener {
	
	private static final Log LOG = LogFactory.getLog(BaseWebAppListener.class);

	public void contextInitialized(ServletContextEvent event) {
		LOG.debug("##### START MUSIC APP !!!!!");
		
		ThreadPoolTaskExecutor baseExecutor = (ThreadPoolTaskExecutor) ApplicationContextProvider.getApplicationContext()
				.getBean("baseExecutor");
		
		baseExecutor.execute(new TaskMonitorService());
		
		// initialize
		cleanupServer();
	}

	public void contextDestroyed(ServletContextEvent event) {
		ThreadPoolTaskExecutor baseExecutor = (ThreadPoolTaskExecutor) ApplicationContextProvider.getApplicationContext()
				.getBean("baseExecutor");	
		baseExecutor.shutdown(); // waitForTasksToCompleteOnShutdown = false

		/**
		 * Here we take care the ThreadPool bean is stopped when the web
		 * application gets stopped. We wait till the Tasks are done.
		 */
		ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) ApplicationContextProvider.getApplicationContext()
					.getBean("taskExecutor");	

		int totalTasks = taskExecutor.getActiveCount();
		LOG.debug("Active Tasks : " + totalTasks);
		taskExecutor.shutdown(); // waitForTasksToCompleteOnShutdown = true
		
		LOG.debug("##### END MUSIC APP !!!!!");
	}

	/**
	 * Verarbeitet die bei einem Shutdown liegengebliebenen Nachrichtendateien,
	 * in dem das lokale Arbeitsverzeichnis nach Eingabedateien durchsucht wird
	 * und diese an den Provider versendet werden.
	 */
	private void cleanupServer() {
		
		try {
			final ServerCleanup serverCleanup = (ServerCleanup) ApplicationContextProvider.getApplicationContext()
					.getBean("serverCleanup");
	
			serverCleanup.cleanupOnStart();
		} catch (Exception e) {
			LOG.error("Error on startup", e);
		}
	}

}
