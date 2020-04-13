package com.example.ingredieat.entity;

public class User {
    public String googleId;
    public String email;
    public String givenName;
    public String familyName;


    public User() {
    }

    public User(String googleId, String email, String givenName, String familyName) {
        this.googleId = googleId;
        this.email = email;
        this.givenName = givenName;
        this.familyName = familyName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }
}
