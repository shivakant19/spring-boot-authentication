package com.example.practice.authentication.exception;

public enum ErrorCodes {
    NOT_NULL_VALIDATION("001"),
    INVALID_DATA("002"),
    BUSINESS_EXCEPTION("003"),
    USER_NOT_FOUND("004"),
    CONTACT_NOT_FOUND("005"),
    USER_ALREADY_EXISTS("006");


    private ErrorCodes(String value){
        this.value = value;
    }
    private String value;

    public String getValue() {
        return value;
    }
}
