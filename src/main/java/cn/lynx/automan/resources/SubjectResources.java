package cn.lynx.automan.resources;

import cn.lynx.automan.handler.subject.SubjectStatuses;
import cn.lynx.automan.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/subjects")
public class SubjectResources {

  @Autowired
  private SubjectService subjectService;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getSubjects(@QueryParam("status") SubjectStatuses status,
                              @QueryParam("page") @DefaultValue("0") int page,
                              @QueryParam("size") @DefaultValue("25") int size) {
    if (status != null) {
      return Response.ok(subjectService.listSubjects(status)).build();
    } else {
      return Response.ok(subjectService.listSubjects(page, size)).build();
    }
  }
}
