package com.henry.util;

import java.text.Normalizer;

public class StringUtils {

    public static String convertSortStringView(String str) {
        String normalized = Normalizer.normalize(str, Normalizer.Form.NFD);
        String ascii = normalized.replaceAll("\\p{M}", "");
        return ascii.toLowerCase();
    }
}
