package com.base.api;

import com.base.base.domain.response.WrapResponse;
import com.base.response.UserDetailResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "auth-query", path = "internal/api/auth-query", url = "${henry.auth-query.api:}")
public interface InternalAuthQueryApi {

    @GetMapping("/users/exit-by-username/{username}")
    WrapResponse<Boolean> exitByUsername(@PathVariable String username);

    @GetMapping("/users/get-user-detail/{username}")
    WrapResponse<UserDetailResponse> getUserDetailsByUsername(@PathVariable String username);

}
