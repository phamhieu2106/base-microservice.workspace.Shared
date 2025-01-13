package com.henry.service;

import com.henry.api.InternalAuthQueryApi;
import com.henry.base.exception.ServiceException;
import com.henry.constant.AuthErrorCode;
import com.henry.constant.UserStatus;
import com.henry.model.CustomUserDetails;
import com.henry.response.UserDetailResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Objects;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class UserDetailsServiceConfig implements UserDetailsService {

    private final InternalAuthQueryApi internalAuthQueryApi;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetailResponse userDetail = internalAuthQueryApi.getUserDetailsByUsername(username).getData();
        if (ObjectUtils.isEmpty(userDetail)) {
            throw new ServiceException(AuthErrorCode.USER_NOT_FOUND);
        }
        if (Objects.equals(userDetail.getStatus(), UserStatus.BLOCKED)) {
            throw new ServiceException(AuthErrorCode.USER_BLOCKED);
        }

        CustomUserDetails userDetails = new CustomUserDetails();
        userDetails.setUsername(username);
        userDetails.setAuthorities(userDetail.getAuthorities().stream()
                .map(SimpleGrantedAuthority::new) // Add ROLE_ prefix
                .collect(Collectors.toList()));
        userDetails.setPassword(userDetail.getPassword());
        userDetails.setStatus(userDetail.getStatus());

        return userDetails;
    }

}
