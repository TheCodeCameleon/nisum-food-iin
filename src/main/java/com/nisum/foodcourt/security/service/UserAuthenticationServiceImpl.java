package com.nisum.foodcourt.security.service;

import com.nisum.foodcourt.service.UserPrincipal;
import com.nisum.foodcourt.modal.JwtAuthenticationResponse;
import com.nisum.foodcourt.modal.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserAuthenticationServiceImpl implements UserAuthenticationService {

    private static final String BEARER_TYPE = "Bearer ";

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    AuthenticationManager authenticationManager;


    @Override
    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.getUserNameOrEmployeeId(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        return ResponseEntity.ok().body(JwtAuthenticationResponse.builder()
                .accessToken(tokenProvider.generateToken(userPrincipal))
                .tokenType(BEARER_TYPE)
                .userNameOrEmployeeId(userPrincipal.getUsername())
                .userId(userPrincipal.getId())
                .roles(userPrincipal.getAuthorities())
                .build());

    }
}
