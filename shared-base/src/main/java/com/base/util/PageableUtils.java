package com.base.util;

import com.base.base.domain.request.BaseSort;
import org.springframework.data.domain.*;

import java.util.List;

public class PageableUtils {

    public static Pageable convertToPageable(int pageNumber, int pageSize) {
        return PageRequest.of(pageNumber, pageSize);
    }

    public static Pageable convertToPageable(int pageNumber, int pageSize, List<BaseSort> sorts) {
        Sort sort = Sort.unsorted();

        if (sorts != null && !sorts.isEmpty()) {
            Sort.Order[] orders = sorts.stream()
                    .map(baseSort -> new Sort.Order(
                            baseSort.isDecreasing() ? Sort.Direction.DESC : Sort.Direction.ASC,
                            baseSort.getField()
                    ))
                    .toArray(Sort.Order[]::new);
            sort = Sort.by(orders);
        }

        return PageRequest.of(pageNumber, pageSize, sort);
    }

    public static <R> Page<R> convertPageToPageResponse(Page<?> page, Class<R> clazzResponse) {
        List<R> content = MappingUtils.mapList(page.getContent(), clazzResponse);
        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());
    }
}
