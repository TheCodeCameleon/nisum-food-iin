package com.nisum.foodcourt.controller;

import com.nisum.foodcourt.modal.JwtAuthenticationResponse;
import com.nisum.foodcourt.modal.LoginRequest;
import com.nisum.foodcourt.modal.SignUpRequest;
import com.nisum.foodcourt.security.service.UserAuthenticationService;
import com.nisum.foodcourt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authenticate")
public class UserAuthController {

    @Autowired
    UserService userService;
    @Autowired
    UserAuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUserLogin(@RequestBody LoginRequest loginRequest) {
        return authenticationService.authenticateUser(loginRequest);
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> authenticateAndCreateUser(@RequestBody SignUpRequest signUpRequest) {
        return userService.persistUser(signUpRequest);
    }

}
