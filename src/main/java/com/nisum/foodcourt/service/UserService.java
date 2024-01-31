package com.nisum.foodcourt.service;

import com.nisum.foodcourt.model.SignUpRequest;
import com.nisum.foodcourt.model.UserDto;
import org.springframework.http.ResponseEntity;


public interface UserService {

    /**
     * @param signUpUser
     * @return
     */
    ResponseEntity<?> persistUser(SignUpRequest signUpUser);

    /**
     * @return
     */
    ResponseEntity<?> getAllUsersList();

    /**
     * @param userId
     * @return
     */
    ResponseEntity<?> getUserById(Integer userId);

    /**
     * @param userDTO
     * @return
     */
    ResponseEntity<?> updateUser(UserDto userDTO);

    /**
     * @param userId
     * @return
     */
    ResponseEntity<?> deleteUser(Integer userId);
}
