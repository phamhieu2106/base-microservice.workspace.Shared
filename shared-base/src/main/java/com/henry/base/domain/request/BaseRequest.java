package com.henry.base.domain.request;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.Serializable;

@Getter
@Setter
public class BaseRequest implements Serializable {
    private String modifiedBy = ObjectUtils.defaultIfNull(SecurityContextHolder.getContext()
            .getAuthentication().getName(), "guest");
}
