package com.nisum.foodcourt.controller;

import com.nisum.foodcourt.model.LoginRequest;
import com.nisum.foodcourt.model.SignUpRequest;
import com.nisum.foodcourt.security.service.UserAuthenticationService;
import com.nisum.foodcourt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/authenticate")
public class UserAuthController {

    @Autowired
    UserService userService;
    @Autowired
    UserAuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUserLogin(@RequestBody @Valid LoginRequest loginRequest) {
        return authenticationService.authenticateUser(loginRequest);
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> authenticateAndCreateUser(@RequestBody @Valid SignUpRequest signUpRequest) {
        return userService.persistUser(signUpRequest);
    }

}
