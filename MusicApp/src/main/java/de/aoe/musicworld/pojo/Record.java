package de.aoe.musicworld.pojo;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author DavidJanicki
 * @version 1.0
 * 
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "record", propOrder = { "title", "name", "genre", "releaseDate", "label", "formats", "trackList" })
@XmlRootElement(name = "record")
public class Record {

	@XmlElement(name = "title")
	private String title;

	@XmlElement(name = "name")
	private String name;

	@XmlElement(name = "genre")
	private String genre;

	@XmlElement(name = "releasedate")
	protected Date releaseDate;

	@XmlElement(name = "label")
	private String label;

	@XmlElement(name = "formats")
	private String formats;

	@XmlElementWrapper(name = "tracklisting")
	@XmlElement(name = "track")
	private List<String> trackList;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getFormats() {
		return formats;
	}

	public void setFormats(String formats) {
		this.formats = formats;
	}

	public List<String> getTrackList() {
		return trackList;
	}

	public void setTrackList(List<String> trackList) {
		this.trackList = trackList;
	}

}
