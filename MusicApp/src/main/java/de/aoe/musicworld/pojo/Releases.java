package de.aoe.musicworld.pojo;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author DavidJanicki
 * @version 1.0
 * 
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "matchingReleases")
public class Releases {

	@XmlElement(name = "release")
	protected List<Release> releaseList = null;

	public List<Release> getReleaseList() {
		return releaseList;
	}

	public void setReleaseList(List<Release> releaseList) {
		this.releaseList = releaseList;
	}

}
