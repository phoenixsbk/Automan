package cn.lynx.automan.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class RootResources {
	
	@GET
	@Path("/version")
	@Produces(MediaType.APPLICATION_JSON)
	public String getVersion() {
		return "{ \"version\": \"1.0.2\" }";
	}
}
