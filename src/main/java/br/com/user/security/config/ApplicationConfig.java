package br.com.user.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
@PropertySource("classpath:/application.properties")
@PropertySource(value = "classpath:application-${run.server.env:default}.properties", ignoreResourceNotFound = true)
public class ApplicationConfig {
    @PostConstruct
    public void init() {
        log.info("Loading Application Configuration");
    }
}
