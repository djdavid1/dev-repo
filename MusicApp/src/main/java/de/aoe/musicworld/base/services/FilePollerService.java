package de.aoe.musicworld.base.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import de.aoe.musicworld.transforms.tasks.RecordsToReleasesTask;
import de.aoe.musicworld.transforms.tasks.AbstractTask;
import de.aoe.musicworld.utils.ApplicationContextProvider;
import de.aoe.musicworld.utils.FileUtils;

public class FilePollerService implements Runnable {

	private static final Log LOG = LogFactory.getLog(FilePollerService.class);

	private String incomingWorkDir;
	private String incomingFileNameFilter;

	private String taskName;

	private String outgoingAdapter;
	private String outgoingWorkDir;

	public FilePollerService(String incomingWorkDir, String incomingFileNameFilter) {
		this.incomingWorkDir = incomingWorkDir;
		this.incomingFileNameFilter = incomingFileNameFilter;
	}

	@Override
	public void run() {
		LOG.info("Initiate FilePollerService ... ");

		ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) ApplicationContextProvider
				.getApplicationContext().getBean("taskExecutor");

		// read the fileNames in the inputfile directory
		String[] fileExtensions = incomingFileNameFilter.split(";");
		List<String> listFilenames = new ArrayList<>();
		
		while (true) {
		try {
			listFilenames = FileUtils.readInputFiles(incomingWorkDir, fileExtensions);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
		
		for (String fileName : listFilenames) {
			LOG.debug("Process file: " + fileName);
			File inputFile = new File(fileName);
			if (!inputFile.canRead()) {
				LOG.error("Input cannot be read!");
			}
			FileInputStream inputStream = null;
			try {
				inputStream = new FileInputStream(inputFile);
			} catch (FileNotFoundException e) {
				LOG.error(e.getMessage(), e);
			}

			// TODO in Factory to create RecordsToReleasesTask + AdapterObject
			AbstractTask task = (AbstractTask) ApplicationContextProvider
					.getApplicationContext().getBean(taskName);
//			RecordsToReleasesTask task = new RecordsToReleasesTask();
			task.setInputStream(inputStream);
//			RecordsToReleasesTask task = new RecordsToReleasesTask(inputStream);
			taskExecutor.execute(task);
		}
		
		try {
			Thread.sleep(60 * 1000L);
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

	public String getOutgoingWorkDir() {
		return outgoingWorkDir;
	}

	public void setOutgoingWorkDir(String outgoingWorkDir) {
		this.outgoingWorkDir = outgoingWorkDir;
	}

	public String getOutgoingAdapter() {
		return outgoingAdapter;
	}

	public void setOutgoingAdapter(String outgoingAdapter) {
		this.outgoingAdapter = outgoingAdapter;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
}