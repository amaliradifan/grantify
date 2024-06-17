package com.example.grantify.model;

public class EditUserRequest {
    private String username;
    private String company;
    private String experience;

    public EditUserRequest(String username, String company, String experience) {
        this.username = username;
        this.company = company;
        this.experience = experience;
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }
}

