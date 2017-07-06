package de.aoe.musicworld.base.services;

import java.io.File;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import de.aoe.musicworld.base.adapter.AbstractAdapter;
import de.aoe.musicworld.transforms.tasks.AbstractTask;
import de.aoe.musicworld.utils.ApplicationContextProvider;
import de.aoe.musicworld.utils.FileUtils;

/**
 * This class implements the FilePollerService.
 * 
 * @author DavidJanicki
 *
 */
public class FilePollerService implements Runnable {

	private static final Log LOG = LogFactory.getLog(FilePollerService.class);

	/** The incomingWorkDir defines incoming file directory. */
	private String incomingWorkDir;

	/** The incomingFileNameFilter defines the criteria to filter the filename.	 */
	private String incomingFileNameFilter;

	/** The taskName defines task to transform the inputStream of file. */
	private String taskName;

	/** The adapterName defines adapter to post process the outputStream. */
	private String adapterName;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		LOG.debug("Run FilePollerService ... ");

		/** Factory method to create taskExecutor */
		ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) ApplicationContextProvider
				.getApplicationContext().getBean("taskExecutor");

		while (true) {
			try {
				/** read the fileNames from the incoming work file directory */
				List<String> listFilenames = FileUtils.readInputFiles(incomingWorkDir, incomingFileNameFilter);

				for (String fileName : listFilenames) {
					LOG.debug("Process file: " + fileName);
					FileInputStream inputStream = new FileInputStream(new File(fileName));

					/** Factory method to create task */
					AbstractTask task = (AbstractTask) ApplicationContextProvider.getApplicationContext()
							.getBean(taskName);
					/** Factory method to create adapter */
					AbstractAdapter adapter = (AbstractAdapter) ApplicationContextProvider.getApplicationContext()
							.getBean(adapterName);

					/** set all needed parameter to execute task - @see Task */
					task.setInputStream(inputStream);
					task.setAdapter(adapter);
					Properties properties = new Properties();
					properties.put("inputFileName", fileName);
					task.setProperties(properties);
					taskExecutor.execute(task);
				}
			} catch (IOException e) {
				LOG.error("Error on reading incoming file", e);
				return;
			}

			try {
				Thread.sleep(20 * 1000L);
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

	public String getIncomingWorkDir() {
		return incomingWorkDir;
	}

	public void setIncomingWorkDir(String incomingWorkDir) {
		this.incomingWorkDir = incomingWorkDir;
	}

	public String getIncomingFileNameFilter() {
		return incomingFileNameFilter;
	}

	public void setIncomingFileNameFilter(String incomingFileNameFilter) {
		this.incomingFileNameFilter = incomingFileNameFilter;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getAdapterName() {
		return adapterName;
	}

	public void setAdapterName(String adapterName) {
		this.adapterName = adapterName;
	}

}