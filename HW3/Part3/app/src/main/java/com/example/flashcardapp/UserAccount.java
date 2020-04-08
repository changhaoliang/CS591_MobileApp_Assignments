package com.example.flashcardapp;

public class UserAccount {
    private String username;
    private String password;

    public UserAccount(String s1, String s2) {
        username = s1;
        password = s2;
    }

    public boolean checkValidAccount(String usernameInput, String passwordInput) {

        return username.equals(usernameInput) && password.equals(passwordInput);
    }
}
