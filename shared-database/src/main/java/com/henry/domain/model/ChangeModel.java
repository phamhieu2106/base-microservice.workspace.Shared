package com.henry.domain.model;

import com.henry.base.domain.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChangeModel extends BaseModel {
    private String fieldName;
    private String prevData;
    private String currentData;
}
