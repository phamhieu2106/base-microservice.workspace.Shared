package com.base.handler;

import com.base.BaseObjectLoggAble;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoggingInterceptor extends BaseObjectLoggAble implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        logger.info("{} '{}' - {}", auth.getName(), request.getMethod(), request.getRequestURI());
        return true;
    }
}
