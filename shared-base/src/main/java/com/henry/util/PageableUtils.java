package com.henry.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class PageableUtils {

    public static Pageable convertToPageable(int pageNumber, int pageSize) {
        return PageRequest.of(pageNumber, pageSize);
    }

    public static <R> Page<R> convertPageToPageResponse(Page<?> page, Class<R> clazzResponse) {
        List<R> content = MappingUtils.mapList(page.getContent(), clazzResponse);
        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());
    }
}
