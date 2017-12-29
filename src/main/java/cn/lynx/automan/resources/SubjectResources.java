package cn.lynx.automan.resources;

import cn.lynx.automan.data.entity.SubjectStatuses;
import cn.lynx.automan.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/subjects")
public class SubjectResources {

  @Autowired
  private SubjectService subjectService;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getSubjectsWithStatus(@QueryParam("status") String status,
                                        @QueryParam("page") @DefaultValue("0") int page,
                                        @QueryParam("size") @DefaultValue("25") int size) {
    if (!StringUtils.isEmpty(status)) {
      for (SubjectStatuses ss : SubjectStatuses.values()) {
        if (ss.toString().equals(status)) {
          return Response.ok(subjectService.listSubjects(ss)).build();
        }
      }
    } else {
      return Response.ok(subjectService.listSubjects(page, size)).build();
    }

    return Response.status(Response.Status.BAD_REQUEST).build();
  }
}
