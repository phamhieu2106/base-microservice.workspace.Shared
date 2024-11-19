package com.henry.base.domain.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BasePageSortRequest extends BasePageRequest {
    private List<BaseSort> sorts;
}
