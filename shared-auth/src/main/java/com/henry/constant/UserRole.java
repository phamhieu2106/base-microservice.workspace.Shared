package com.henry.constant;

import lombok.Getter;

import java.util.List;

@Getter
public class UserRole {
    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";

    public static final List<String> ALL_ROLE = List.of(ADMIN, USER);
}
