package com.henry.base.service.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class BaseRequest implements Serializable {
    private String modifiedBy;
}
