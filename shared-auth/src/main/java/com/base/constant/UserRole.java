package com.base.constant;

import lombok.Getter;

import java.util.List;

@Getter
public class UserRole {
    public static final String ADMIN = "ROLE_ADMIN";
    public static final String USER = "ROLE_USER";

    public static final List<String> ALL_ROLE = List.of(ADMIN, USER);
}

