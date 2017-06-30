package de.aoe.musicworld.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
		
		// initialize
		cleanupServer();
	}

	public void contextDestroyed(ServletContextEvent event) {
		// destroy

		/**
		 * Here we take care the WorkerPool bean is stopped when the web
		 * application gets stopped.
		 */
		// TODO Pool
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
