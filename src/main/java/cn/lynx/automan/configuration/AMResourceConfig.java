package cn.lynx.automan.configuration;

import cn.lynx.automan.filters.AuthFilter;
import cn.lynx.automan.resources.*;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;

@Component
@ApplicationPath("/rest")
public class AMResourceConfig extends ResourceConfig {
  public AMResourceConfig() {
    register(AuthFilter.class);
    register(RootResources.class);
    register(AuthResources.class);
    register(UserResources.class);
    register(SubjectResources.class);
    register(ThreadResources.class);
  }
}
