package cn.lynx.automan.configuration;

import javax.ws.rs.ApplicationPath;

import cn.lynx.automan.resources.RootResources;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
@ApplicationPath("/rest")
public class AutomanResourceConfig extends ResourceConfig {
	public AutomanResourceConfig() {
		register(RootResources.class);
	}
}
