package com.nisum.foodcourt.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequest {

    String userNameOrEmployeeId;

    String password;
}
