package com.nisum.foodcourt.resource.constant;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public enum SecurityClaim {

    USER_ID("userId"),
    USER_NAME("userName"),
    ROLES("roles"),
    FULL_NAME("fullName"),
    EMPLOYEE_ID("employeeId"),

    SERVER_EXPIRY_KEY("domainKey");

    String value;

    public String getValue() {
        return value;
    }
}
