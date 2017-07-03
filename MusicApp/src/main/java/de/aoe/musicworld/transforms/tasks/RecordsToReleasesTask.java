package de.aoe.musicworld.transforms.tasks;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.aoe.musicworld.pojo.Record;
import de.aoe.musicworld.pojo.Records;
import de.aoe.musicworld.pojo.Release;
import de.aoe.musicworld.pojo.Releases;
import de.aoe.musicworld.transforms.RecordsJAXBConverter;
import de.aoe.musicworld.transforms.ReleasesJAXBConverter;

public class RecordsToReleasesTask extends AbstractTask {

	private static final Log LOG = LogFactory.getLog(RecordsToReleasesTask.class);

	private InputStream inputStream;

	/**
	 * Hidden Constructor.
	 */
	protected RecordsToReleasesTask() {
	}
	
	@Override
	public void run() {
		RecordsJAXBConverter recordsConverter = new RecordsJAXBConverter();
		Records records = null;
		try {
			records = (Records) recordsConverter.unmarshal(inputStream);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				LOG.error(e.getMessage(), e);
			}
		}
		if (records == null)
			return;

		Releases releases = new Releases();
		List<Release> releaseList = new ArrayList<>();
		for (Record record : records.getRecordList()) {
			Release release = new Release();
			release.setName(record.getName());
			release.setTrackCount(record.getTrackList().size());
			releaseList.add(release);
		}
		releases.setReleaseList(releaseList);

		ReleasesJAXBConverter releaseConverter = new ReleasesJAXBConverter();
		OutputStream outputStream = null;
		try {
			outputStream = releaseConverter.marshal(releases);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		ByteArrayOutputStream baos = (ByteArrayOutputStream) outputStream;
		if (baos != null) {
			try {
				String content = baos.toString("UTF-8");
				LOG.info(content);
			} catch (UnsupportedEncodingException e) {
				LOG.error(e.getMessage(), e);
			}
		} else {
			LOG.error("Output content is empty");
		}
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

}
