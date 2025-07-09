package com.base.constant;

import java.util.HashMap;
import java.util.Map;

public class NotificationStatus {
    public static final int UNREAD = 0;
    public static final int READ = 1;

    public static final Map<Integer, String> map;

    static {
        map = new HashMap<>();
        map.put(UNREAD, "Chưa đọc");
        map.put(READ, "Đã đọc");
    }
}
