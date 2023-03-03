package com.nisum.foodcourt.service;

import com.nisum.foodcourt.entity.User;
import com.nisum.foodcourt.modal.SignUpRequest;
import com.nisum.foodcourt.modal.UserModal;
import com.nisum.foodcourt.repository.UserRepository;
import com.nisum.foodcourt.resource.ResponseExceptionMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public ResponseEntity<?> persistUser(SignUpRequest signUpUser) {

        checkUserExistanceValidation(signUpUser);

        User userPersisted = User.builder()
                .userName(signUpUser.getUserName())
                .password(encoder.encode(signUpUser.getPassword()))
                .email(signUpUser.getEmail())
                .contactNo(signUpUser.getContactNo())
                .employeeId(signUpUser.getEmployeeId())
                .fullName(signUpUser.getFullName())
                .roles(signUpUser.getRoles()).build();

        userPersisted = userRepository.save(userPersisted);

        UserModal userModal = convertUserEntityToUserModal(userPersisted);

        return ResponseEntity.ok(userModal);

    }

    @Override
    public ResponseEntity<?> getUserById(Integer userId) {
        User user = userRepository.findById(userId).get();



        return ResponseEntity.ok().body(convertUserEntityToUserModal(user));
    }

//    public void getSomething() throws Exception {
//         ResponseEntity entity = getUserById(1);
//        if(entity.getStatusCode() == HttpStatus.OK ) {
//            UserModal userModal = (UserModal) entity.getBody();
//        }
//    }

    /**
     *
     * @param userEntity
     * @return
     * this method is converting userEntity to userModal without password attribute
     */

    private static UserModal convertUserEntityToUserModal(User userEntity) {
        return UserModal.builder()
                .id(userEntity.getId())
                .userName(userEntity.getUserName())
                .email(userEntity.getEmail())
                .contactNo(userEntity.getContactNo())
                .employeeId(userEntity.getEmployeeId())
                .fullName(userEntity.getFullName())
                .roles(userEntity.getRoles()).build();
    }

    private ResponseEntity<String> checkUserExistanceValidation(SignUpRequest user) {
        String responseExceptionMessage = "";

        if(userRepository.existsByUserName(user.getUserName())) {
            responseExceptionMessage = ResponseExceptionMessage.USERNAME_ALREADY_EXIST.getException();
        }

        if(userRepository.existsByEmail(user.getEmail())) {
            responseExceptionMessage = ResponseExceptionMessage.EMAIL_ALREADY_EXIST.getException();
        }

        if(userRepository.existsByEmployeeId(user.getEmployeeId())) {
            responseExceptionMessage = ResponseExceptionMessage.EMPLOYEE_ALREADY_EXIST.getException();
        }
        return ResponseEntity
                .badRequest()
                .body(responseExceptionMessage);
    }

}
