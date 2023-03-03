package com.nisum.foodcourt.repository;

import com.nisum.foodcourt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Boolean existsByUserName(String userName);

    Boolean existsByEmail(String email);

    Boolean existsByEmployeeId(String employeeId);

    Optional<User> findByUserNameOrEmployeeId(String userName, String employeeId);

}
