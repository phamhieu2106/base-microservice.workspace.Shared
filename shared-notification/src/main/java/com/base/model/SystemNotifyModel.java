package com.base.model;

import com.base.domain.BaseModel;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SystemNotifyModel extends BaseModel {
    private String type;
}
