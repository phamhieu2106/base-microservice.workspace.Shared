package com.base.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WorkflowConnectorModel {
    private String name;
    private WorkflowConfigConnectorModel config;
}
