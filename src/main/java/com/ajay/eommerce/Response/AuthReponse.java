package com.ajay.eommerce.Response;

public class AuthReponse {
    private String jwt;
    private String message;

    public AuthReponse(String jwt, String message) {
        super();
        this.jwt = jwt;
        this.message = message;
    }

    public AuthReponse() {

    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
