package com.base.request;

import com.base.base.domain.request.BasePageSortRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QueryUserRequest extends BasePageSortRequest {
    private String keyword;

    private String phoneNumber;
    private String email;
    private String username;
    private List<String> status;

    private Date fromDateOfBirth;
    private Date toDateOfBirth;

    private Date fromCreatedDate;
    private Date toCreatedDate;
}
