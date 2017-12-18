package cn.lynx.automan.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("cn.lynx.automan.data.repo")
@ConfigurationProperties(prefix = "automan")
public class AutoConfigure {
}
