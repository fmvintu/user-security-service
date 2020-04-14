package br.com.user.security.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

@Slf4j
public class ApplicationSecurityCondition implements Condition {
    public static final String ENABLE_SECURITY_PROP_NAME = "application.security.enabled";

    public static final Boolean DEFAULT_ENABLE_SECURITY_VALUE = Boolean.FALSE;

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Environment environment = context.getEnvironment();

        log.info("Enforced security: [" + environment.getProperty(ENABLE_SECURITY_PROP_NAME) + "]");

        return environment.getProperty(ENABLE_SECURITY_PROP_NAME, Boolean.class, DEFAULT_ENABLE_SECURITY_VALUE);
    }
}
