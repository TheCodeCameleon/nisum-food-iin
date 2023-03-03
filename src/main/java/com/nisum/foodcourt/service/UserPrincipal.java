package com.nisum.foodcourt.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nisum.foodcourt.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;


@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class UserPrincipal implements UserDetails {

    Integer id;

    String fullName;

    String userName;

    String email;

    String employeeId;

    @JsonIgnore
    String password;

    Collection<? extends GrantedAuthority> authorities;

    public static UserPrincipal createPrincipal(User user) {
        return UserPrincipal.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .employeeId(user.getEmployeeId())
                .password(user.getPassword())
                .authorities(user.getRoles().stream().map(
                        role -> new SimpleGrantedAuthority(role.getRoleName().name())).collect(Collectors.toList())
                )
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
