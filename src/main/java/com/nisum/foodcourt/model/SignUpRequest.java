package com.nisum.foodcourt.model;

import com.nisum.foodcourt.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@AllArgsConstructor
public class SignUpRequest {


    @NotBlank(message = "Please provide email:")
    String email;

    @NotBlank(message = "Please provide Full name")
    String fullName;

    @NotBlank(message = "Please provide username:")
    String userName;

    @Size(min = 8, max = 16)
    @NotBlank(message = "Please provide password:")
    String password;

    @Size(max = 6)
    @NotBlank(message = "Please provide employeeId:")
    String employeeId;

    String contactNo;

    Set<Role> roles;
}
