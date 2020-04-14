package br.com.user.security.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.caffeine.CaffeineCacheManager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
public class FlexibleCaffeineCacheManager extends CaffeineCacheManager {
    private final ConcurrentMap<String, Caffeine<Object, Object>> builders = new ConcurrentHashMap<>();

    private String cacheDefaultSpec;

    private CacheLoader cacheLoader;

    @Override
    @SuppressWarnings("unchecked")
    protected Cache<Object, Object> createNativeCaffeineCache(String name) {
        Caffeine<Object, Object> builder = builders.getOrDefault(name, Caffeine.from(cacheDefaultSpec));

        log.info("Create native: [{}] with builder: [{}]", name, builder);
        if (isNull(builder)) {
            return super.createNativeCaffeineCache(name);
        }

        if (nonNull(this.cacheLoader)) {
            return builder.build(this.cacheLoader);
        } else {
            return builder.build();
        }
    }

    public void setCacheDefaultSpec(String cacheDefaultSpec) {
        this.cacheDefaultSpec = cacheDefaultSpec;
    }

    public void createCachesWithSpec(Map<String, String> cacheAndSpec) {
        log.info("Configuring caches with: [{}]", cacheAndSpec);
        cacheAndSpec.entrySet().stream()
                .forEach(e -> builders.put(e.getKey(), Caffeine.from(e.getValue())));
    }
}
