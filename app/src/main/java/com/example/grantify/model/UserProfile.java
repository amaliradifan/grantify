package com.example.grantify.model;

import com.google.gson.annotations.SerializedName;

public class UserProfile {
    @SerializedName("id")
    private String id;

    @SerializedName("username")
    private String username;

    @SerializedName("email")
    private String email;
    
    @SerializedName("company")
    private String company;

    @SerializedName("experience")
    private String experience;

    @SerializedName("image")
    private String image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
