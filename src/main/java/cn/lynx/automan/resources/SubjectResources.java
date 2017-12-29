package cn.lynx.automan.resources;

import cn.lynx.automan.data.entity.SubjectStatuses;
import cn.lynx.automan.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/subjects")
public class SubjectResources {

  @Autowired
  private SubjectService subjectService;

  @GET
  @Path("/status/{status}")
  public Response getSubjectsWithStatus(@PathParam("status") String status) {
    for (SubjectStatuses ss : SubjectStatuses.values()) {
      if (ss.toString().equals(status)) {
        return Response.ok(subjectService.listSubjects(ss)).build();
      }
    }

    return Response.status(Response.Status.BAD_REQUEST).build();
  }
}
