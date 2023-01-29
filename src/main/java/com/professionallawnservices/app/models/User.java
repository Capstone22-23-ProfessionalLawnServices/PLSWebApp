package com.professionallawnservices.app.models;

import java.util.ArrayList;

public class User {

    private String email;

    private String password;

    private ArrayList<String> rolls;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword() {
        this.password = password;
    }

    public ArrayList<String> getRolls() {
        return rolls;
    }

    public void setRolls(ArrayList<String> rolls) {
        this.rolls = rolls;
    }

}
