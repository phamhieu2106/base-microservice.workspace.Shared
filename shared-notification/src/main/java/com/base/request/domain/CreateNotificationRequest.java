package com.base.request.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateNotificationRequest implements Serializable {
    private String recipient; //username receive
    @NotBlank
    private String message;
    @NotNull
    private Integer type;
}
