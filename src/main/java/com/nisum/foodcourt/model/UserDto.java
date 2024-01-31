package com.nisum.foodcourt.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nisum.foodcourt.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private Integer id;
    @NotBlank
    private String userName;
    @JsonIgnore
    @NotBlank
    private String password;
    private String fullName;
    @Email
    private String email;
    private String contactNo;
    private String employeeId;
    private Set<Role> roles = new HashSet<>();

}
