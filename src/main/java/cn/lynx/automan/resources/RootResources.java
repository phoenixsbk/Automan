package cn.lynx.automan.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.Map;

@Path("/")
public class RootResources {

    @GET
    @Path("/version")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> getVersion() {
        return Collections.singletonMap("version", "");
    }
}
