package com.nisum.foodcourt.security;

import com.nisum.foodcourt.resource.constant.SecurityExceptionMessages;
import com.nisum.foodcourt.resource.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    private final DateUtil dateUtil;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        log.error(SecurityExceptionMessages.USER_UNAUTHORIZED.getValue(), dateUtil.getCurrentDate(), authException.getMessage());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, SecurityExceptionMessages.UNAUTHORIZED_ERROR.getValue());
    }

}
