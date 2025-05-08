package com.example.cs157proj.fxmlControllers;

public final class usernameHolder {
    private String username;
    private final static usernameHolder instance = new usernameHolder();
    private usernameHolder() {}

    public static usernameHolder getInstance() {
        return instance;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}
