package com.henry.base.func;

import com.henry.base.repository.HistoryRepository;
import com.henry.base.request.QueryHistoryRequest;
import com.henry.base.response.HistoryResponse;
import com.henry.domain.entity.HistoryEntity;
import com.henry.util.PageableUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QueryHistoryFunc extends BaseFunc {

    private final HistoryRepository historyRepository;

    public Page<HistoryResponse> exec(QueryHistoryRequest request) {
        Pageable pageable = PageableUtils.convertToPageable(request.getPageNumber(), request.getPageSize(), request.getSorts());

        Page<HistoryEntity> pageHistories = historyRepository.findAllByEntityId(request.getEntityId(), pageable);

        return PageableUtils.convertPageToPageResponse(pageHistories, HistoryResponse.class);
    }
}
