package br.com.user.security.cache;

import java.util.HashMap;
import java.util.Map;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@ConfigurationProperties(prefix = "caffeine")
public class CacheConfigProperties {

    @Data
    public static class Spec {
        @NotNull
        @NotBlank
        String specs;
    }

    private Map<String, Spec> cache = new HashMap<>();
}
