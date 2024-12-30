package com.henry.base.request;

import com.henry.base.domain.request.BasePageSortRequest;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QueryHistoryRequest extends BasePageSortRequest {
    private String entityId;
}
