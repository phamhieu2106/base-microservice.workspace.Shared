package com.henry.constant;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class UserRole {
    public static final int ADMIN = 0;
    public static final int USER = 1;

    private static final Map<Integer, String> map;

    static {
        map = new HashMap<>();
        map.put(USER, "USER");
        map.put(ADMIN, "ADMIN");
    }

}
