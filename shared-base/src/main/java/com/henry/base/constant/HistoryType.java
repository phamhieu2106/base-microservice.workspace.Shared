package com.henry.base.constant;

import java.util.HashMap;
import java.util.Map;

public class HistoryType {
    public static final int CREATE = 1;
    public static final int UPDATE = 2;
    public static final int BLOCK = -1;
    public static final int DELETE = 0;
    public static final int AUTO_UPDATE = 3;
    public static final int ADD = 4;

    public static Map<Integer, String> historyTypeMap;

    static {
        historyTypeMap = new HashMap<>();
        historyTypeMap.put(CREATE, "CREATED");
        historyTypeMap.put(UPDATE, "UPDATED");
        historyTypeMap.put(BLOCK, "BLOCKED");
        historyTypeMap.put(DELETE, "DELETED");
        historyTypeMap.put(AUTO_UPDATE, "SYSTEM_AUTO_UPDATED");
        historyTypeMap.put(ADD, "ADDED");
    }
}
