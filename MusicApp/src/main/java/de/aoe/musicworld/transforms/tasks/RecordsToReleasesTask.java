package de.aoe.musicworld.transforms.tasks;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.aoe.musicworld.base.adapter.AbstractAdapter;
import de.aoe.musicworld.pojo.Record;
import de.aoe.musicworld.pojo.Records;
import de.aoe.musicworld.pojo.Release;
import de.aoe.musicworld.pojo.Releases;
import de.aoe.musicworld.transforms.RecordsJAXBConverter;
import de.aoe.musicworld.transforms.ReleasesJAXBConverter;
import de.aoe.musicworld.utils.ApplicationContextProvider;
import de.aoe.musicworld.utils.StringUtils;

public class RecordsToReleasesTask extends AbstractTask {

	private static final Log LOG = LogFactory.getLog(RecordsToReleasesTask.class);

	/** The input inputStream to transform. */
	private InputStream inputStream;

	/** The adapter to post process the outputStream. */
	private String adapterName;

	/** The global properties of the Task */
	private Properties properties;
	
	/** The task id of the Task */
	private Long taskId;

	/** Log TaskId */
	private String LOG_TASKID;
	
	@Override
	public void run() {
		this.setTaskId(Thread.currentThread().getId());
		/** put taskId to properties for logging purposes */
		properties.put("taskId", getTaskId().toString());
		LOG_TASKID = "Task #" + this.getTaskId() + " - ";
		
		LOG.debug(LOG_TASKID + "Run " + this.getClass().getSimpleName() + " ... ");
		transformRecordsToReleaseV1();		
		LOG.debug(LOG_TASKID + "End " + this.getClass().getSimpleName());
	}
	
	private void transformRecordsToReleaseV1(){
		LOG.debug(LOG_TASKID + "Start transformRecordsToReleaseV1");
		
		/**
		 * This part unmarshal the inputStream to object over the JAXBConverter
		 */
		RecordsJAXBConverter recordsConverter = new RecordsJAXBConverter();
		Records records = null;
		try {
			records = (Records) recordsConverter.unmarshal(inputStream);
		} catch (Exception e) {
			LOG.error(LOG_TASKID + e.getMessage(), e);
			return;
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				LOG.error(LOG_TASKID + e.getMessage(), e);
			}
		}
		if (records == null) {
			LOG.warn(LOG_TASKID + "Unmarshaled JAXBObject is empty");
			return;
		}		
		LOG.debug(LOG_TASKID + "Unmarshaled InputStream to Object over RecordsJAXBConverter");

		/**
		 * This part implements the functional requirement of the
		 * transformation. We want to have a list of releases with more than 10
		 * tracks and a release date before 01/01/2001.
		 */
		Date targetDate;
		try {
			targetDate = StringUtils.stringToDate("01/01/2011");
		} catch (ParseException e) {
			LOG.error(LOG_TASKID + "The value cannot be converted to Date", e);
			return;
		}
		Releases releases = new Releases();
		List<Release> releaseList = new ArrayList<>();
		for (Record record : records.getRecordList()) {
			if (record.getTrackList().size() > 10 && record.getReleaseDate() != null
					&& record.getReleaseDate().before(targetDate)) {
				Release release = new Release();
				release.setName(record.getName());
				release.setTrackCount(record.getTrackList().size());
				releaseList.add(release);
			}
		}
		releases.setReleaseList(releaseList);
		LOG.debug(LOG_TASKID + "Transformed Records to Release");

		/**
		 * This part marshal the object to outputStream over the JAXBConverter
		 */
		ReleasesJAXBConverter releaseConverter = new ReleasesJAXBConverter();
		OutputStream outputStream = null;
		try {
			outputStream = releaseConverter.marshal(releases);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return;
		}
		LOG.debug(LOG_TASKID + "Marshalled OutputStream over RecordsJAXBConverter");
		
		callAdapterV1(outputStream);
		
		LOG.debug(LOG_TASKID + "End transformRecordsToReleaseV1");
	}
	
	private void callAdapterV1(OutputStream outputStream){
		if(outputStream == null)
			return;
		
		/** Factory method to create prototype adapter */
		AbstractAdapter adapter = (AbstractAdapter) ApplicationContextProvider.getApplicationContext()
				.getBean(adapterName);
		
		/** This part call the adapter to process */
		adapter.setProperties(properties);
		adapter.setOutputStream(outputStream);
		adapter.postProcess();	
		LOG.debug(LOG_TASKID + "Called AdapterV1");
	}

	@Override
	protected AbstractTask createTask() {
		return new RecordsToReleasesTask();
	}

	@Override
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	@Override
	public InputStream getInputStream() {
		return inputStream;
	}



	@Override
	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	@Override
	public Properties getProperties() {
		return properties;
	}

	@Override
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	@Override
	public Long getTaskId() {		
		return taskId;
	}

	@Override
	public void setAdapterName(String adapterName) {
		this.adapterName = adapterName;
	}

	@Override
	public String getAdapterName() {		
		return adapterName;
	}



}
