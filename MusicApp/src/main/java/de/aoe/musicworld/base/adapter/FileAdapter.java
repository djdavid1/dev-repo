package de.aoe.musicworld.base.adapter;

import java.io.File;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.aoe.musicworld.utils.BaseFileUtils;

/**
 * This class implements an concrete Adapter which can be processed by any Task. 
 * 
 * @author DavidJanicki
 *
 */
public class FileAdapter extends AbstractAdapter {

	private static final Log LOG = LogFactory.getLog(FileAdapter.class);

	/** The outputStream to put in file. */
	private OutputStream outputStream;

	/** The outgoingWorkDir to place file in. */
	private String outgoingWorkDir;

	/** The outgoingFilePrefix to prefixing filename. */
	private String outgoingFilePrefix;

	/** The outgoingFileExtension to set extension to filename. */
	private String outgoingFileExtension;

	/** The properties to read global parameters of the task. */
	private Properties properties;

	/*
	 * (non-Javadoc)
	 * @see de.aoe.musicworld.base.adapter.AbstractAdapter#postProcess()
	 */
	public void postProcess() {
		String TaskId = properties.getProperty("taskId");
		final String TASKID = "Task #" + TaskId + " - ";
		LOG.debug(TASKID + "Process FileAdapter ... ");

		/* generates unique FileName */
		String uniqueFileName = BaseFileUtils.generateUniqueFileName(outgoingFilePrefix, outgoingFileExtension);
		File outputFile = new File(outgoingWorkDir.concat(uniqueFileName));
		
		/* writes file with content of the outputStream
		 * on Exception return - the file will be polled again 
		 */
		try {
			BaseFileUtils.outputStreamToFile(outputStream, outputFile);
		} catch (IOException e) {
			LOG.error(TASKID + e.getMessage(), e);
			return;
		}
		LOG.debug(TASKID + "Written file to " + outputFile.getAbsolutePath());

		/* if file is written, the inputfile will be removed
		 */
		String inputFileName = properties.getProperty("inputFileName");
		if (inputFileName != null) {
			File inputFile = new File(inputFileName);
			if (inputFile.canRead() && !inputFile.delete()) {
				LOG.error(TASKID + "Error on delete file " + inputFileName);
				return;
			}
		}
		LOG.debug(TASKID + "Removed file from " + inputFileName);
	}

	@Override
	protected AbstractAdapter createAdapter() {
		return new FileAdapter();
	}

	@Override
	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

	@Override
	public OutputStream getOutputStream() {
		return outputStream;
	}

	public String getOutgoingWorkDir() {
		return outgoingWorkDir;
	}

	public void setOutgoingWorkDir(String outgoingWorkDir) {
		this.outgoingWorkDir = outgoingWorkDir;
	}

	public String getOutgoingFilePrefix() {
		return outgoingFilePrefix;
	}

	public void setOutgoingFilePrefix(String outgoingFilePrefix) {
		this.outgoingFilePrefix = outgoingFilePrefix;
	}

	public String getOutgoingFileExtension() {
		return outgoingFileExtension;
	}

	public void setOutgoingFileExtension(String outgoingFileExtension) {
		this.outgoingFileExtension = outgoingFileExtension;
	}

	@Override
	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	@Override
	public Properties getProperties() {
		return properties;
	}

}