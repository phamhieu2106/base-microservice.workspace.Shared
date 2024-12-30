package com.henry.base.response;

import com.henry.domain.model.ChangeModel;
import lombok.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class HistoryResponse {
    private String id;
    private String entityId;
    private String entityCode;
    private String entityName;
    private Integer type;
    private String content;
    private Date createdDate;
    private List<ChangeModel> _changes;
}
