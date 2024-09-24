package com.henry.base.service.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Sort {
    private String fieldName;
    private boolean decreasing = true;
}
