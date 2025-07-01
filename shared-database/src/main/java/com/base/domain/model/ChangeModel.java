package com.base.domain.model;

import com.base.domain.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChangeModel extends BaseModel {
    private String modelName;
    private String fieldName;
    private Object action;
    private Object prevData;
    private Object currentData;
}
