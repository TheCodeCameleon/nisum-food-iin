package com.nisum.foodcourt.resource.constant;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SecurityExceptionMessages {

    INVALID_SIGNATURE("Invalid JWT signature: {}"),
    INVALID_TOKEN("Invalid JWT token: {}"),
    TOKEN_EXPIRED("JWT token is expired: {}"),
    UNSUPPORTED_TOKEN("JWT token is unsupported: {}"),
    EMPTY_CLAIM("JWT claims string is empty: {}"),
    USER_UNAUTHORIZED("Unauthorized error at {} : {}"),
    UNAUTHORIZED_ERROR("Unauthorized error at {} : {}");

    String value;

    public String getValue() {
        return value;
    }
}
