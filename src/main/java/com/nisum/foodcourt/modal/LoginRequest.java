package com.nisum.foodcourt.modal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequest {

    String userNameOrEmployeeId;

    String password;
}
