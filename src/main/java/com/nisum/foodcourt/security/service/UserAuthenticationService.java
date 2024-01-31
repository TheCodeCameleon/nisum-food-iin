package com.nisum.foodcourt.security.service;

import com.nisum.foodcourt.model.LoginRequest;
import org.springframework.http.ResponseEntity;

public interface UserAuthenticationService {

    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest);
}
