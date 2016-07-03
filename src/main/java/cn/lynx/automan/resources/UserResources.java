package cn.lynx.automan.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import cn.lynx.automan.db.entity.User;
import cn.lynx.automan.db.entity.UserState;
import cn.lynx.automan.db.repo.UserRepository;
import cn.lynx.automan.resources.model.UserRes;
import cn.lynx.automan.resources.model.UserStateRes;

@Path("/users")
public class UserResources {
	
	@Autowired
	private UserRepository repo;
	
	@GET
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
	}
}
