package br.com.user.security.domain.entity.enu;

import java.util.Optional;

import static java.util.Arrays.stream;

public enum RoleEnum {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER"),
    MONITOR("ROLE_MONITOR"),
    PARTNER("ROLE_PARTNER");

    private final String roleValue;

    RoleEnum(String roleValue) {
        this.roleValue = roleValue;
    }

    public String getRoleValue() {
        return roleValue;
    }

    public static Optional<RoleEnum> fromValue(String roleValue) {
        return stream(values())
                    .filter(r -> r.getRoleValue().equals(roleValue))
                .findAny();
    }
}
