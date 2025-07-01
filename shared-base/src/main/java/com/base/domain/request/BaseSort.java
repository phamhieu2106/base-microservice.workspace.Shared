package com.base.domain.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseSort {
    private String field;
    private boolean decreasing = true;
}
