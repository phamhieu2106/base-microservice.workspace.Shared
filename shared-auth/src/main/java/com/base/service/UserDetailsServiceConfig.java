package com.base.service;

import com.base.api.InternalAuthQueryApi;
import com.base.base.exception.ServiceException;
import com.base.constant.AuthErrorCode;
import com.base.constant.UserStatus;
import com.base.model.CustomUserDetails;
import com.base.response.UserDetailResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Collectors;

@Service
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
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList()));
        userDetails.setPassword(userDetail.getPassword());
        userDetails.setStatus(userDetail.getStatus());

        return userDetails;
    }

}
