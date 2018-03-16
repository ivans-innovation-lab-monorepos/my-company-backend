package com.idugalic.common.model;

public enum EventError {

    BLOGPOST_1("Blog Post is not a draft", "BLOGPOST_1");

    private String message;
    private String code;

    EventError(String message, String code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

    public String getId() {
        return name();
    }

    @Override
    public String toString() {
        return getMessage();
    }
}
