package com.base.util;

import com.base.exception.ServiceException;
import com.base.constant.AuthErrorCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class PermissionUtils {

    public static void hasRole(List<String> roles) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Optional<GrantedAuthority> found = Optional.empty();
        for (GrantedAuthority authority : authorities) {
            if (roles.contains(authority.getAuthority())) {
                found = Optional.of(authority);
                break;
            }
        }
        if (found.isEmpty()) {
            throw new ServiceException(AuthErrorCode.DONT_HAVE_PERMISSION);
        }
    }

    public static void hasRole(String role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Optional<GrantedAuthority> found = Optional.empty();
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals(role)) {
                found = Optional.of(authority);
                break;
            }
        }
        if (found.isEmpty()) {
            throw new ServiceException(AuthErrorCode.DONT_HAVE_PERMISSION);
        }
    }
}
