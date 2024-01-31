package com.nisum.foodcourt.resource;

public enum ResponseExceptionMessage {

    USERNAME_ALREADY_EXIST("username already exist"),
    EMAIL_ALREADY_EXIST("user email already exist"),
    EMPLOYEE_ALREADY_EXIST("employee id already exist"),
    USER_NOT_FOUND("requested user not found"),
    USER_DELETED_SUCCESSFULLY("user deleted successfully"),

    MENU_NOT_FOUND("No menu item found"),
    MENU_DELETED_SUCCESSFULLY("menu deleted successfully");


    String exceptionMessage;

    ResponseExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public String getMessage() {
        return this.exceptionMessage;
    }


}
