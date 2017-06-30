package de.aoe.musicworld.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author DavidJanicki
 * @version 1.0
 * 
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "release", propOrder = { "name", "trackCount"})
@XmlRootElement(name = "release")
public class Release {

	@XmlElement(name = "name")
	private String name;

	@XmlElement(name = "trackcount")
	private int trackCount;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTrackCount() {
		return trackCount;
	}

	public void setTrackCount(int trackCount) {
		this.trackCount = trackCount;
	}

}
