package br.com.user.security.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@Slf4j
@EnableCaching
@Configuration
@EnableConfigurationProperties(CacheConfigProperties.class)
public class CacheConfig {

    @Value("${caffeine.cache.default.spec}")
    private String cacheDefaultSpec;

    @Autowired
    private CacheConfigProperties cacheConfigProperties;

    @PostConstruct
    public void init() {
        log.info("Loading Cache Configuration");
    }

    @Bean
    public CacheManager cacheManager() {
        log.info("Configuring Caffeine CacheManager with: [{}] and default spec: [{}]",
                cacheConfigProperties, cacheDefaultSpec);
        FlexibleCaffeineCacheManager caffeineCacheManager = new FlexibleCaffeineCacheManager();
        caffeineCacheManager.setCacheDefaultSpec(cacheDefaultSpec);
        caffeineCacheManager.setAllowNullValues(false);
        caffeineCacheManager.createCachesWithSpec(getCacheNameAndSpec());

        return caffeineCacheManager;
    }

    private Map<String, String> getCacheNameAndSpec() {
        return cacheConfigProperties.getCache().entrySet().stream()
                .collect(toMap(e -> e.getKey().toUpperCase(), e -> e.getValue().getSpecs()));
    }
}
