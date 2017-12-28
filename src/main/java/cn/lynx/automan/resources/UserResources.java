package cn.lynx.automan.resources;

import cn.lynx.automan.data.entity.User;
import cn.lynx.automan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/user")
public class UserResources {
  @Context
  private ContainerRequestContext requestContext;

  @Autowired
  private UserService userService;

  @Path("/{username}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getUser(@PathParam("username") String username) {
    User user = userService.findUser(username);
    if (user != null) {
      return Response.ok(user).build();
    } else {
      return Response.status(Response.Status.NOT_FOUND).build();
    }
  }
	
	/*@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserRes> getUsers() {
		List<User> users = repo.findAll();
		List<UserRes> result = new ArrayList<>();
		
		for (User u : users) {
			UserRes ur = new UserRes();
			ur.setUsername(u.getUsername());
			ur.setBirthday(u.getBirthday());
			ur.setEmail(u.getEmail());
			ur.setGender(u.getGender());
			ur.setGrade(u.getGrade());
			ur.setMobile(u.getMobile());
			ur.setRealName(u.getRealName());
			ur.setSignature(u.getSignature());
			
			UserState us = u.getUserState();
			UserStateRes usr = new UserStateRes();
			
			usr.setCurrency(us.getCurrency());
			usr.setExpLevel(us.getExpLevel());
			usr.setLastIp(us.getLastIp());
			usr.setLastLoginTime(us.getLastLoginTime());
			usr.setLoginTimes(us.getLoginTimes());
			usr.setPublishTimes(us.getPublishTimes());
			usr.setRole(us.getRole());
			usr.setStatus(us.getStatus());
			
			ur.setUserState(usr);
			
			result.add(ur);
		}
		
		return result;
	}*/
}
