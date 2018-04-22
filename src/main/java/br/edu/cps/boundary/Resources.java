package br.edu.cps.boundary;

import java.net.URI;
import java.net.URISyntaxException;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import br.edu.cps.control.DataController;
import br.edu.cps.entity.Album;
import br.edu.cps.entity.Artist;
import br.edu.cps.entity.Track;

@Path("resources")
public class Resources {

	private DataController data = new DataController ();
	private Gson gson = new Gson();
	
	//Paths
	private final String localPath = "http://localhost:8085/aura/api/resources/";
	private final String artistsPathGET = "/artists";
	private final String artistPathGET = "/artist/{id}";
	private final String artistsPathPOST = "/artist";
	private final String artistPathPUT = "/artist/{id}";
	private final String artistPathDEL = "/artist/{id}";
	private final String albumPathGET = "{artistid}/{albumid}";
	private final String trackPathGET = "{artistid}/{albumid}/{tracknumber}";
	
	//insert new artist
	@POST
	@Path(artistsPathPOST)
	public Response addArtist (@QueryParam("name") String name,
							   @QueryParam("bio") String bio) {
		
		int count = data.addArtist(new Artist (-1, name, bio));
		String uri = new StringBuilder(localPath)
						.append("artist/")
						.append(String.valueOf(count)).toString();
		try {
			return Response.created(new URI(uri)).build();
		} catch (URISyntaxException e) {
			return Response.status(418).build();
		}
	}
	
	//return artist by id
	@GET
	@Path(artistPathGET)
	public Response getArtist (@PathParam("id") int id) {
		
		String json = null;
		Artist temp = null;
		JsonArray ja = new JsonArray();
		JsonObject jobj = null;
		JsonObject jobj2 = null;
		Album[] albumsTemp = null;
		
		if(data.isArtistsEmpty())
			return Response.noContent().build();
		
		temp = data.getArtist(id);

		if(temp == null)
			return Response.status(404).build();

		albumsTemp = temp.getAlbuns();
	
		jobj = gson.toJsonTree(temp).getAsJsonObject();
		jobj.remove("albums");
		
		for(Album x : albumsTemp){
			jobj2 = new JsonObject();
			jobj2.addProperty("link", new StringBuilder(localPath)
					.append(String.valueOf(temp.getArtistID())).append("/")
					.append(String.valueOf(x.getAlbumID())).toString());
			ja.add(jobj2);
		}
		
		jobj.add("albums", ja);
		json = gson.toJson(jobj);
		return Response.ok(json, MediaType.APPLICATION_JSON).build();
	}

	//update artist by id
	@PUT
	@Path(artistPathPUT)
	public Response setArtist (@PathParam("id") int id,
								  @QueryParam("name") String name,
								  @QueryParam("bio") String bio) {
		
		String json = null;
		
		if(data.isArtistsEmpty())
			return Response.noContent().build();
	
		if(data.getArtist(id) == null)
			return Response.status(404).build();
		
		data.setArtist(id, name, bio);
		
		json = gson.toJson(data.getArtist(id));
		return Response.ok(json, MediaType.APPLICATION_JSON).build();
	}

	//delete artist
	@DELETE
	@Path(artistPathDEL)
	public Response removeArtist (@PathParam("id") int id) {
		
		String json = null;
		
		if(data.getArtists().isEmpty())
			return Response.noContent().build();
		
		if(data.getArtist(id) == null)
			return Response.status(404).build();
		
		json = gson.toJson(data.removeArtist(id));
		return Response.ok(json, MediaType.APPLICATION_JSON).build();
	}

	//return artist list
	@GET
	@Path(artistsPathGET)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getArtists () {
		
		String json = null;
		JsonArray jaArtists = new JsonArray();
		JsonArray jaAlbums = new JsonArray();
		JsonObject jobj = new JsonObject();
		JsonObject jobj2 = new JsonObject();
		
		if(data.isArtistsEmpty())
			return Response.noContent().build();
		
		for(Artist x : data.getArtists()){
			
			jobj = gson.toJsonTree(x).getAsJsonObject();
			jobj.remove("albums");
			jaAlbums = new JsonArray();
			
			if(x.hasAbum()) {
  				for(Album z : x.getAlbuns()) {
  					jobj2 = new JsonObject();
					jobj2.addProperty("link", new StringBuilder(localPath)
												.append(String.valueOf(x.getArtistID())).append("/")
												.append(String.valueOf(z.getAlbumID())).toString());
					jaAlbums.add(jobj2);
				}
			}
			jobj.add("Albums", jaAlbums);
			jaArtists.add(jobj);
		}

		json = gson.toJson(jaArtists);
		return Response.ok(json, MediaType.APPLICATION_JSON).build();
	}
	
	@GET
	@Path(albumPathGET)
	public Response getAlbum (@PathParam("artistid") int artistId,
							   @PathParam("albumid") int albumId) {
		
		Artist artTemp;
		Album albTemp;
		String json = null;
		JsonObject jobj = null;
		JsonObject jobj2 = null;
		
		if(data.getArtists().isEmpty())
			return Response.noContent().build();

		artTemp = data.getArtist(artistId);

		if(artTemp == null)
			return Response.status(404).build();
		
		if(!artTemp.hasAbum())
			return Response.status(404).build();

		albTemp = artTemp.getAlbum(albumId);
		
		if(albTemp == null)
			return Response.status(404).build();
		
		jobj = gson.toJsonTree(albTemp).getAsJsonObject();
		
		for(int x = 0; x < albTemp.getTrackList().size(); x++) {
			jobj2 = jobj.get("trackList").getAsJsonArray().get(x).getAsJsonObject();
			jobj2.addProperty("link", new StringBuilder(localPath)
										.append(String.valueOf(artistId)).append("/")
										.append(String.valueOf(albumId)).append("/")
										.append(String.valueOf(albTemp.getTrackList().get(x).getTrackID()))
										.toString());
		}

		json = gson.toJson(jobj);
		return Response.ok(json, MediaType.APPLICATION_JSON).build();
	}	
	
	@GET
	@Path(trackPathGET)
	public Response getTrack(@PathParam("artistid") int artistId,
							  @PathParam("albumid") int albumId,
							  @PathParam("tracknumber") int trackNumber){
		
		Artist artTemp;
		Album albTemp;
		Track traTemp;
		String json = null;

		artTemp = data.getArtist(artistId);
		
		if(artTemp == null)
			return Response.status(404).build();

		if(!artTemp.hasAbum())
			return Response.status(404).build();

		albTemp = artTemp.getAlbum(albumId);
		
		if(albTemp == null || !albTemp.hasTrack())
			return Response.status(404).build();

		traTemp = albTemp.getTrack(trackNumber);
		
		if(traTemp == null)
			return Response.status(404).build();
		
		json = gson.toJson(traTemp);
		return Response.ok(json, MediaType.APPLICATION_JSON).build();
	}
}