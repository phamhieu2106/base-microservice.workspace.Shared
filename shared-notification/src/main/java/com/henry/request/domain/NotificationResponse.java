package com.henry.request.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotificationResponse {
    private String id;
    private String recipient;
    private String message;
    private Integer status;
    private String type;
    private Date createdDate;
}
