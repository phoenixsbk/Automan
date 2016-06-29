package cn.lynx.automan;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
@ApplicationPath("/rest")
public class AutomanResourceConfig extends ResourceConfig {
	public AutomanResourceConfig() {
		packages("cn.lynx.automan.resources");
	}
}
