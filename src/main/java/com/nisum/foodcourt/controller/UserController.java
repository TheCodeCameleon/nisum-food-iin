package com.nisum.foodcourt.controller;


import com.nisum.foodcourt.model.UserDto;
import com.nisum.foodcourt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllUsersList() {
        return userService.getAllUsersList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Integer userId) {
        return userService.getUserById(userId);
    }

    @PutMapping()
    public ResponseEntity<?> updateUser(@RequestBody UserDto userDTO) {
        return userService.updateUser(userDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Integer userId) {
        return userService.deleteUser(userId);
    }

}
