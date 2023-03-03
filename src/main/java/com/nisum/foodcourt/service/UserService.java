package com.nisum.foodcourt.service;

import com.nisum.foodcourt.modal.SignUpRequest;
import com.nisum.foodcourt.modal.UserModal;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<?> persistUser(SignUpRequest signUpUser);

    ResponseEntity<?> getUserById(Integer userId);
}
