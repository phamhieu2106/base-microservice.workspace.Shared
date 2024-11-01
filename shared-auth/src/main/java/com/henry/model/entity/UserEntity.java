package com.henry.model.entity;

import com.henry.base.domain.BaseEntity;
import com.henry.constant.CustomJDBCType;
import com.henry.converter.JDBCConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
@Entity
@Table
public class UserEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String fullName;
    private Date dateOfBirth;
    private String username;
    private String phoneNumber;
    private String email;
    private String password;
    @Convert(converter = JDBCConverter.class)
    @Column(columnDefinition = CustomJDBCType.JSON)
    private List<GrantedAuthority> authorities;
}
