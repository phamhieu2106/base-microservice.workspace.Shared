package com.base.request.constant;

import java.util.HashMap;
import java.util.Map;

public class NotificationType {
    public static final int SINGLE = 0;
    public static final int GROUP = 1;

    public static final Map<Integer, String> map;

    static {
        map = new HashMap<>();
        map.put(SINGLE, "Cá nhân");
        map.put(GROUP, "Nhóm");
    }
}
