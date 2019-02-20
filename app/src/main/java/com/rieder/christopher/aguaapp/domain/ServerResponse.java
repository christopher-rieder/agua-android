package com.rieder.christopher.aguaapp.domain;

public class ServerResponse {
    String message;

    public ServerResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;

    }

    public void setMessage(String message) {
        this.message = message;
    }
}
