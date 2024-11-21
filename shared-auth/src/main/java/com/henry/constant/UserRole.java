package com.henry.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum UserRole implements GrantedAuthority {
    USER("USER"),
    ADMIN("ADMIN");

    private final String value;
    private static final Map<UserRole, String> map;

    static {
        map = new HashMap<>();
        map.put(USER, "USER");
        map.put(ADMIN, "ADMIN");
    }

    @Override
    public String getAuthority() {
        return map.get(this);
    }
}
