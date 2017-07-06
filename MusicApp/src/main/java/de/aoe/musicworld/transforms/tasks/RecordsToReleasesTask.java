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
import de.aoe.musicworld.utils.StringUtils;

public class RecordsToReleasesTask extends AbstractTask {

	private static final Log LOG = LogFactory.getLog(RecordsToReleasesTask.class);

	/** The input inputStream to transform. */
	private InputStream inputStream;

	/** The adapter adapter to post process the outputStream. */
	private AbstractAdapter adapter;

	/** The global properties of the Task */
	private Properties properties;
	
	/** The task id of the Task */
	private Long taskId;

	@Override
	public void run() {
		this.setTaskId(Thread.currentThread().getId());
		final String TASKID = "Task #" + this.getTaskId() + " - ";
		LOG.debug(TASKID + "Run " + this.getClass().getSimpleName() + " ... ");

		/**
		 * This part unmarshal the inputStream to object over the JAXBConverter
		 */
		RecordsJAXBConverter recordsConverter = new RecordsJAXBConverter();
		Records records = null;
		try {
			records = (Records) recordsConverter.unmarshal(inputStream);
		} catch (Exception e) {
			LOG.error(TASKID + e.getMessage(), e);
			return;
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				LOG.error(TASKID + e.getMessage(), e);
			}
		}
		if (records == null) {
			LOG.warn(TASKID + "Unmarshaled JAXBObject is empty");
			return;
		}		
		LOG.debug(TASKID + "Unmarshaled InputStream to Object over RecordsJAXBConverter");

		/**
		 * This part implements the functional requirement of the
		 * transformation. We want to have a list of releases with more than 10
		 * tracks and a release date before 01/01/2001.
		 */
		Date targetDate;
		try {
			targetDate = StringUtils.stringToDate("01/01/2011");
		} catch (ParseException e) {
			LOG.error(TASKID + "The value cannot be converted to Date", e);
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
		LOG.debug(TASKID + "Transformed Records to Release");

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
		LOG.debug(TASKID + "Marshalled OutputStream over RecordsJAXBConverter");

		/** This part call the adapter to process */
		properties.put("taskId", getTaskId().toString());
		adapter.setProperties(properties);
		adapter.setOutputStream(outputStream);
		adapter.postProcess();
		
		LOG.debug(TASKID + "End " + this.getClass().getSimpleName());
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
	public void setAdapter(AbstractAdapter adapter) {
		this.adapter = adapter;
	}

	@Override
	public AbstractAdapter getAbstractAdapter() {
		return adapter;
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



}
