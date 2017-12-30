package cn.lynx.automan.resources;

import cn.lynx.automan.data.entity.AThread;
import cn.lynx.automan.service.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/threads")
public class ThreadResources {
  @Autowired
  private ThreadService threadService;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAllThreadsFromSubject(@QueryParam("subjectId") int subjectId) {
    if (subjectId <= 0) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }

    List<AThread> threadList = threadService.listThreadFromSubject(subjectId);
    return Response.ok(threadList).build();
  }
}
