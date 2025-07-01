package com.base.request.api;

import com.base.domain.response.WrapResponse;
import com.base.request.domain.CreateNotificationRequest;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notification-server", path = "internal/server/notification-server", url = "http://localhost:7003")
public interface InternalNotificationAPI {

    @PostMapping("/notifications/create")
    WrapResponse<String> createNotification(@Valid @RequestBody CreateNotificationRequest request);
}
