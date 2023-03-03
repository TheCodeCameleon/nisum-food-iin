package com.nisum.foodcourt.security.service;

import com.nisum.foodcourt.service.UserPrincipal;

import com.nisum.foodcourt.resource.constant.SecurityClaim;
import com.nisum.foodcourt.resource.constant.SecurityExceptionMessages;
import com.nisum.foodcourt.resource.utils.DateUtil;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.rmi.server.UID;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.*;

@Slf4j
@Component
public class JwtTokenProvider {

    private static final Long ExpirationTimeOfDay = 24L;

    private static final String tokenValidationKey = new UID().toString();

    @Value("${app.jwtSecret}")
    private String secret;

    @Autowired
    private DateUtil dateUtility;



    public String generateToken(UserPrincipal userPrincipal) {
        HashMap<String, Object> claims = generateClaimsMapForToken(userPrincipal);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(dateUtility.getCurrentDate())
                .setExpiration(dateUtility.getCurrentDateByTime(TimeUnit.HOURS.toMillis(1)))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public HashMap<String, Object> getUserContextFromJwtToken(String token) {
        HashMap<String, Object> claimsMap = new HashMap<>();

        Claims tokenClaims = getAllClaimsFromToken(token);

        claimsMap.put(SecurityClaim.USER_NAME.getValue(), tokenClaims.get(SecurityClaim.USER_NAME.getValue()));
        claimsMap.put(SecurityClaim.USER_ID.getValue(), tokenClaims.get(SecurityClaim.USER_ID.getValue()));
        claimsMap.put(SecurityClaim.EMPLOYEE_ID.getValue(), tokenClaims.get(SecurityClaim.EMPLOYEE_ID.getValue()));
        claimsMap.put(SecurityClaim.ROLES.getValue(), tokenClaims.get(SecurityClaim.ROLES.getValue()));
        claimsMap.put(SecurityClaim.SERVER_EXPIRY_KEY.getValue(), tokenClaims.get(SecurityClaim.SERVER_EXPIRY_KEY.getValue()));

        return claimsMap;
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private HashMap<String, Object> generateClaimsMapForToken(UserPrincipal userPrincipal) {
        HashMap<String, Object> claimsMap = new HashMap<>();
        List<String> authorities = userPrincipal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        claimsMap.put(SecurityClaim.USER_ID.getValue(), userPrincipal.getId());
        claimsMap.put(SecurityClaim.USER_NAME.getValue(), userPrincipal.getUsername());
        claimsMap.put(SecurityClaim.ROLES.getValue(), authorities);
        claimsMap.put(SecurityClaim.FULL_NAME.getValue(), userPrincipal.getFullName());
        claimsMap.put(SecurityClaim.EMPLOYEE_ID.getValue(), userPrincipal.getEmployeeId());
        claimsMap.put(SecurityClaim.SERVER_EXPIRY_KEY.getValue(), tokenValidationKey);
        return claimsMap;
    }

    public String getUserNameFromJwtToken(String token) {
        return extractSpecifiedClaim(token, Claims::getSubject);
    }

    public boolean validateToken(String authToken) {
        try {

            Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            log.error(SecurityExceptionMessages.INVALID_SIGNATURE.getValue(), ex.getMessage());
        } catch (MalformedJwtException ex) {
            log.error(SecurityExceptionMessages.INVALID_TOKEN.getValue(), ex.getMessage());
        } catch (ExpiredJwtException ex) {
            log.error(SecurityExceptionMessages.TOKEN_EXPIRED.getValue(), ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            log.error(SecurityExceptionMessages.UNSUPPORTED_TOKEN.getValue(), ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.error(SecurityExceptionMessages.EMPTY_CLAIM.getValue(), ex.getMessage());
        }

        return false;
    }

    public boolean validateJwtToken(String token) {
        return getUserContextFromJwtToken(token).get("domainKey").equals(tokenValidationKey) && validateToken(token);
    }

    private <T> T extractSpecifiedClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims= getAllClaimsFromToken(token);
        return claimResolver.apply(claims);
    }

}
