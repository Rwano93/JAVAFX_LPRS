package com.gestionevenements.utils;

public class SessionManager {
    private static SessionManager instance;
    private String loggedInUser;
    private String userRole;

    private SessionManager() {}

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void setLoggedInUser(String email, String role) {
        this.loggedInUser = email;
        this.userRole = role;
    }

    public String getLoggedInUser() {
        return loggedInUser;
    }

    public String getUserRole() {
        return userRole;
    }

    public void logout() {
        this.loggedInUser = null;
        this.userRole = null;
    }
}

