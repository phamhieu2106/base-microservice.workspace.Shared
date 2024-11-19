package com.henry.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


public class PageableUtils {

    public static Pageable convertToPageable(int pageNumber, int pageSize) {
        return PageRequest.of(pageNumber, pageSize);
    }
}
