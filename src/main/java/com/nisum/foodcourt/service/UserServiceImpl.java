package com.nisum.foodcourt.service;

import com.nisum.foodcourt.entity.User;
import com.nisum.foodcourt.model.SignUpRequest;
import com.nisum.foodcourt.model.UserDto;
import com.nisum.foodcourt.repository.UserRepository;
import com.nisum.foodcourt.resource.ResponseExceptionMessage;
import com.nisum.foodcourt.resource.util.ConversionUtil;
import com.nisum.foodcourt.resource.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PaymentTransactionService paymentTransactionService;

    private final PasswordEncoder encoder;

    private final DateUtil dateUtil;

    private final ConversionUtil conversionUtil;

    @Override
    @Transactional
    public ResponseEntity<?> persistUser(SignUpRequest signUpUser) {

        log.info("Checking user existence validation at : {}", dateUtil.getCurrentTimeStamp());

        checkUserExistenceValidation(signUpUser);

        log.info("persisting new User in the system at :{}", dateUtil.getCurrentTimeStamp());

        User userPersisted = User.builder()
                .userName(signUpUser.getUserName())
                .password(encoder.encode(signUpUser.getPassword()))
                .email(signUpUser.getEmail())
                .contactNo(signUpUser.getContactNo())
                .employeeId(signUpUser.getEmployeeId())
                .fullName(signUpUser.getFullName())
                .roles(signUpUser.getRoles())
                .wallet(paymentTransactionService.createUserWallet())
                .build();

        userPersisted = userRepository.save(userPersisted);

        log.info("user persisted successfully");

        UserDto userDTO = convertUserEntityToUserModal(userPersisted);

        return ResponseEntity.ok(userDTO);

    }

    @Override
    public ResponseEntity<?> getAllUsersList() {
        log.info("Getting All user started at: {}", dateUtil.getCurrentTimeStamp());
        List<User> userList = userRepository.findAll();
        return userList.isEmpty() ?
                ResponseEntity.badRequest().body(ResponseExceptionMessage.USER_NOT_FOUND) :
                ResponseEntity.ok(userList.stream()
                        .map(this::convertUserEntityToUserModal)
                        .collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<?> getUserById(Integer userId) {
        log.info("Getting user by id started at: {}", dateUtil.getCurrentTimeStamp());

        Optional<User> user = userRepository.findById(userId);
        return isUserExist(userId) ?
                ResponseEntity.ok(conversionUtil.mapEntityToModel(user, UserDto.class)) :
                ResponseEntity.badRequest().body(ResponseExceptionMessage.USER_NOT_FOUND);

    }

    @Override
    @Transactional
    public ResponseEntity<?> updateUser(UserDto userDTO) {
        User user = null;
        log.info("updating user started at :{}", dateUtil.getCurrentTimeStamp());
        if (isUserExist(userDTO.getId())) {
            user = userRepository.save(conversionUtil.mapModelToEntity(userDTO, User.class));
        }
        return Objects.isNull(user) ?
                ResponseEntity.badRequest().body(ResponseExceptionMessage.USER_NOT_FOUND) :
                ResponseEntity.ok(conversionUtil.mapEntityToModel(user, UserDto.class));
    }

    /**
     * @param userId
     * @return
     */

    @Override
    @Transactional
    public ResponseEntity<?> deleteUser(Integer userId) {

        log.info("Deleting user started at: {}", dateUtil.getCurrentTimeStamp());
        int confirmationValue = 0;
        if (isUserExist(userId)) {
            confirmationValue = userRepository.softDeleteUser(userId);
            log.info("User deleted successfully at: {}", dateUtil.getCurrentTimeStamp());
        }

        return confirmationValue == 1 ?
                ResponseEntity.ok(ResponseExceptionMessage.USER_DELETED_SUCCESSFULLY) :
                ResponseEntity.badRequest().body(ResponseExceptionMessage.USER_NOT_FOUND);

    }

    /**
     * @param userEntity
     * @return this method is converting userEntity to userModal without password attribute
     */

    private UserDto convertUserEntityToUserModal(User userEntity) {
        return UserDto.builder()
                .id(userEntity.getId())
                .userName(userEntity.getUserName())
                .email(userEntity.getEmail())
                .contactNo(userEntity.getContactNo())
                .employeeId(userEntity.getEmployeeId())
                .fullName(userEntity.getFullName())
                .roles(userEntity.getRoles()).build();
    }

    private boolean isUserExist(Integer id) {
        return userRepository.existsById(id);
    }

    /**
     * checking for user existence in database for persistence
     *
     * @param user
     * @return ResponseEntity
     */

    private ResponseEntity<String> checkUserExistenceValidation(SignUpRequest user) {
        String responseExceptionMessage = "";

        if (userRepository.existsByUserName(user.getUserName())) {
            responseExceptionMessage = ResponseExceptionMessage.USERNAME_ALREADY_EXIST.getMessage();
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            responseExceptionMessage = ResponseExceptionMessage.EMAIL_ALREADY_EXIST.getMessage();
        }

        if (userRepository.existsByEmployeeId(user.getEmployeeId())) {
            responseExceptionMessage = ResponseExceptionMessage.EMPLOYEE_ALREADY_EXIST.getMessage();
        }
        return ResponseEntity
                .badRequest()
                .body(responseExceptionMessage);
    }

}
