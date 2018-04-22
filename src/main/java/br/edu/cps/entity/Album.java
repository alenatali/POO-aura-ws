package br.edu.cps.entity;

import java.util.ArrayList;
import java.util.List;

public class Album {

	private short albumID;
	private String name;
	private String date;
	private String description;
	private AlbumType albumType;
	private List<Track> trackList =  new ArrayList<Track>();
	
	public Album(short albumID,
				 String name,
				 String date,
				 String description,
				 AlbumType albumType,
				 List<Track> trackList) {
		this.albumID = albumID;
		this.name = name;
		this.date = date;
		this.description = description;
		this.albumType = albumType;
		this.trackList = trackList;
	}
	
	public short getAlbumID() {
		return albumID;
	}

	public void setAlbumID(short albumID) {
		this.albumID = albumID;
	}	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public AlbumType getAlbumType() {
		return albumType;
	}
	public void setAlbumType(AlbumType albumType) {
		this.albumType = albumType;
	}
	public List<Track> getTrackList() {
		return trackList;
	}
	public void setTrackList(List<Track> trackList) {
		this.trackList = trackList;
	}
	public Track getTrack (int id) {
		for(int x = 0; x < trackList.size(); x++) {
			if(trackList.get(x).getTrackID() == id) {
				return trackList.get(x);
			}
		}
		return null;
	}

	public boolean hasTrack () {
		return (trackList.size() == 0)? false : true;
	}
	
}
