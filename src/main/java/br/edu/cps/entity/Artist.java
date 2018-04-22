package br.edu.cps.entity;

import java.util.ArrayList;
import java.util.List;

public class Artist {

	private int artistID;
	private String name;
	private String bio;
	private List<Album> albums = new ArrayList<Album>();
	
	public Artist(int artistID, String name, String bio) {
		this.artistID = artistID;
		this.name = name;
		this.bio = bio;
	}
	
	public int getArtistID() {
		return artistID;
	}
	public void setArtistID(int artistId) {
		this.artistID = artistId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
	
	public void addAlbum (Album...a) {
		for(Album x: a) {
			albums.add(x);
		}
	}
	
	public Album[] getAlbuns () {
		
		Album [] temp;
		
		if(albums.isEmpty()) {
			return temp = new Album[0];
		}
		temp = new Album[albums.size()];
		return albums.toArray(temp);
	}
	
	public Album getAlbum (int id) {
		for(int x = 0; x < albums.size(); x++) {
			if (albums.get(x).getAlbumID() == id) {
				return albums.get(x);
			}
		}
		return null;
	}
	public boolean hasAbum () {
		return (albums.size() == 0)? false : true;
	}
}
