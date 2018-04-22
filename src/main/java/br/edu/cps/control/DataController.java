package br.edu.cps.control;

import java.util.ArrayList;
import java.util.List;

import br.edu.cps.entity.Album;
import br.edu.cps.entity.AlbumType;
import br.edu.cps.entity.Artist;
import br.edu.cps.entity.Track;

public class DataController {

	private static List<Artist> artists = new ArrayList<Artist>();
	private static int count;

	public DataController() {

		if (count == 0) {
			List<Track> auxList = new ArrayList<Track>();

			Artist art1 = new Artist(count(), "Joe", "Joe's bio");
			Artist art2 = new Artist(count(), "Joane", "Joane's bio");
			Artist art3 = new Artist(count(), "Lisa", "Lisa's bio");

			Track t11 = new Track("track 1", (short) 1, "t1's lyric");
			Track t12 = new Track("track 2", (short) 2, "t2's lyric");
			Track t13 = new Track("track 3", (short) 3, "t3's lyric");
			// --
			Track t21 = new Track("track 1", (short) 1, "t1's lyric");
			Track t22 = new Track("track 2", (short) 2, "t2's lyric");
			// --
			Track t31 = new Track("track 1", (short) 1, "t1's lyric");

			Track[] trackList1 = new Track[] { t11, t12, t13 };
			Track[] trackList2 = new Track[] { t21, t22 };

			for (Track x : trackList1) {
				auxList.add(x);
			}
			Album a1 = new Album((short) 1, "Album 1", "02/05/2019", "Album's description", AlbumType.EP, auxList);

			auxList = new ArrayList<Track>();
			for (Track x : trackList2) {
				auxList.add(x);
			}
			Album a2 = new Album((short) 1, "Album 2", "09/03/2020", "Album's description", AlbumType.Single, auxList);

			auxList = new ArrayList<Track>();
			auxList.add(t31);
			Album a3 = new Album((short) 2, "Album 2", "10/01/2022", "Album's description", AlbumType.Single, auxList);

			art1.addAlbum(a1, a3);
			art2.addAlbum(a2);
			artists.add(art1);
			artists.add(art2);
			artists.add(art3);
		}
	}

	public int addArtist(Artist a) {
		a.setArtistID(count());
		artists.add(a);
		return a.getArtistID();
	}

	public Artist getArtist(int id) {
		for (int x = 0; x < artists.size(); x++) {
			if (artists.get(x).getArtistID() == id) {
				return (Artist) artists.get(x);
			}
		}
		return null;
	}

	public void setArtist(int id, String name, String bio) {
		for (int x = 0; x < artists.size(); x++) {
			if (artists.get(x).getArtistID() == id) {
				Artist temp = artists.get(x);
				temp.setName(name);
				temp.setBio(bio);
				artists.set(x, temp);
				break;
			}
		}
	}

	public Artist removeArtist(int id) {
		Artist temp = null;
		for (int x = 0; x < artists.size(); x++) {
			if (artists.get(x).getArtistID() == id) {
				temp = artists.get(x);
				artists.remove(x);
				break;
			}
		}
		return temp;
	}

	public ArrayList<Artist> getArtists() {
		return (ArrayList<Artist>) artists;
	}

	public boolean isArtistsEmpty() {
		return artists.isEmpty();
	}

	private int count() {
		count++;
		return count - 1;
	}
}
