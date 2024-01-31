package com.nisum.foodcourt.security;

import com.nisum.foodcourt.resource.constant.SecurityClaim;
import com.nisum.foodcourt.security.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER_TYPE = "Bearer ";
    private static final int BEARER_SIZE = BEARER_TYPE.length();

    private final JwtTokenProvider jwtUtils;
    private final CustomUserDetailService userDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        ExtendedHttpServletRequest extendedHttpServletRequest = new ExtendedHttpServletRequest(request);
        String token = getTokenFromHeader(request);
        if (StringUtils.hasText(token) && jwtUtils.validateToken(token)) {
            String userName = jwtUtils.getUserNameFromJwtToken(token);
            UserDetails userDetails = userDetailService.loadUserByUsername(userName);
            UsernamePasswordAuthenticationToken authenticatedToken = UsernamePasswordAuthenticationToken.authenticated(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
            authenticatedToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticatedToken);

            extendedHttpServletRequest = populateRequestClaims(token, request);

        }
        filterChain.doFilter(extendedHttpServletRequest, response);
    }

    private ExtendedHttpServletRequest populateRequestClaims(String jwtToken, HttpServletRequest request) {
        Map<String, Object> userContextMap = jwtUtils.getUserContextFromJwtToken(jwtToken);
        ExtendedHttpServletRequest extendedHttpServletRequest = new ExtendedHttpServletRequest(request);
        extendedHttpServletRequest.addHeader(SecurityClaim.USER_ID.getValue(), userContextMap.get(SecurityClaim.USER_ID.getValue()).toString());
        extendedHttpServletRequest.addHeader(SecurityClaim.USER_NAME.getValue(), userContextMap.get(SecurityClaim.USER_NAME.getValue()).toString());
        extendedHttpServletRequest.addHeader(SecurityClaim.ROLES.getValue(), userContextMap.get(SecurityClaim.ROLES.getValue()).toString());
        extendedHttpServletRequest.addHeader(SecurityClaim.EMPLOYEE_ID.getValue(), userContextMap.get(SecurityClaim.EMPLOYEE_ID.getValue()).toString());
        return extendedHttpServletRequest;
    }

    private String getTokenFromHeader(HttpServletRequest request) {
        String authHeader = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(authHeader) && authHeader.startsWith(BEARER_TYPE)) {
            return authHeader.substring(BEARER_SIZE);
        }
        return null;
    }
}
