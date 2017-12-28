package cn.lynx.automan.configuration;

import javax.ws.rs.ApplicationPath;

import cn.lynx.automan.filters.AuthFilter;
import cn.lynx.automan.resources.AuthResources;
import cn.lynx.automan.resources.RootResources;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
@ApplicationPath("/rest")
public class AMResourceConfig extends ResourceConfig {
	public AMResourceConfig() {
		register(AuthFilter.class);
		register(RootResources.class);
		register(AuthResources.class);
	}
}