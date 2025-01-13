package com.henry.constant;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class UserStatus {
    public static final int INACTIVE = 0;
    public static final int ACTIVE = 1;
    public static final int BLOCKED = -1;


    private static final Map<Integer, String> map;

    static {
        map = new HashMap<>();
        map.put(ACTIVE, "Đã kích hoạt");
        map.put(INACTIVE, "Chưa kích hoạt");
        map.put(BLOCKED, "Bị chặn");
    }
}
