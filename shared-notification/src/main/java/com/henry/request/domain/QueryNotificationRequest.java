package com.henry.request.domain;

import com.henry.base.domain.request.BasePageSortRequest;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class QueryNotificationRequest extends BasePageSortRequest implements Serializable {
    private String recipient;
    private Integer status;
}
