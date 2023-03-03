package com.nisum.foodcourt.security;

import com.nisum.foodcourt.entity.User;
import com.nisum.foodcourt.service.UserPrincipal;
import com.nisum.foodcourt.repository.UserRepository;
import com.nisum.foodcourt.resource.ResponseExceptionMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmployeeId) throws UsernameNotFoundException{

        User user = userRepository.findByUserNameOrEmployeeId(usernameOrEmployeeId, usernameOrEmployeeId)
                .orElseThrow(() -> new UsernameNotFoundException(ResponseExceptionMessage.USER_NOT_FOUND.getException() + " : " + usernameOrEmployeeId));

        return UserPrincipal.createPrincipal(user);
    }
}
