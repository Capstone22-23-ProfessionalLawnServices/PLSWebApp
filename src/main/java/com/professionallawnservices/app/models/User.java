package com.professionallawnservices.app.models;

import java.util.ArrayList;

public class User {

    private String username;

    private String password;

    private ArrayList<String> rolls;

    public String getUsername() {
        return username;
    }

    public void setUsername(String email) {
        this.username = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<String> getRolls() {
        return rolls;
    }

    public void setRolls(ArrayList<String> rolls) {
        this.rolls = rolls;
    }

}
