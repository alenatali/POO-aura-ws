package br.edu.cps.entity;

public class Track {
	
	private String name;
	private short trackID;
	private String lyric;

	public Track(String name, short trackID, String lyric) {
		this.name = name;
		this.trackID= trackID;
		this.lyric = lyric;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public short getTrackID() {
		return trackID;
	}

	public void setTrackID(short trackID) {
		this.trackID = trackID;
	}

	public String getLyric() {
		return lyric;
	}

	public void setLyric(String lyric) {
		this.lyric = lyric;
	}
}
