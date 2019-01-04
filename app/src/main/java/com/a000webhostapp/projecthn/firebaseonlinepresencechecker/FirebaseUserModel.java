package com.a000webhostapp.projecthn.firebaseonlinepresencechecker;

import com.google.firebase.database.Exclude;

class FirebaseUserModel {

    private String name;
    private boolean online;
    private String email;

    public FirebaseUserModel() {
        /*Blank default constructor essential for Firebase*/
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    @Exclude
    @Override
    public String toString() {
        return getName();
    }

}
