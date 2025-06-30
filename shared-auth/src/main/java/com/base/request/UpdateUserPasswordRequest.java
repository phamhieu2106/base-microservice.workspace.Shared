package com.base.request;

import com.base.constant.AuthErrorCode;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserPasswordRequest {
    @NotBlank(message = AuthErrorCode.USER_USERNAME_EMPTY)
    private String username;
    @NotBlank(message = AuthErrorCode.PASSWORD_EMPTY)
    private String password;
    @NotBlank(message = AuthErrorCode.CONFIRM_PASSWORD_EMPTY)
    private String confirmPassword;
}
