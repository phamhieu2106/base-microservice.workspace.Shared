package com.base.base.request;

import com.base.base.domain.request.BasePageSortRequest;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QueryHistoryRequest extends BasePageSortRequest {
    private String entityId;
}
