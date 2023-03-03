package com.nisum.foodcourt.security.service;

import com.nisum.foodcourt.modal.JwtAuthenticationResponse;
import com.nisum.foodcourt.modal.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface UserAuthenticationService {

    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest);
}
