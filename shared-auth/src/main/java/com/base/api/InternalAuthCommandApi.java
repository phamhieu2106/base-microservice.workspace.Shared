package com.base.api;

import com.base.base.domain.response.WrapResponse;
import com.base.request.CreateUserRequest;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "auth-command", path = "internal/api/auth-command", url = "${henry.auth-command.api:}")
public interface InternalAuthCommandApi {
    //User
    @PostMapping("/users/create")
    WrapResponse<String> create(@Valid @RequestBody CreateUserRequest request, @RequestParam String currentUsername);
}
