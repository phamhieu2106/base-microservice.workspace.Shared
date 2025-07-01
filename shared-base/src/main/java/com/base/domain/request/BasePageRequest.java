package com.base.domain.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasePageRequest {
    private int pageNumber = 0;
    private int pageSize = 10;
}
