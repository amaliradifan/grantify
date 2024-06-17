package com.example.grantify.model;

public class LoginResponse {
    private String message;
    private String accessToken;
    private long expirationTime; // Waktu kedaluwarsa token dalam milidetik

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return accessToken;
    }

    public void setToken(String token) {
        this.accessToken = token;
    }

    public long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(long expirationTime) {
        this.expirationTime = expirationTime;
    }
}
