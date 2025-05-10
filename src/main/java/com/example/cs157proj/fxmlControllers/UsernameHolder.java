package com.example.cs157proj.fxmlControllers;

public final class UsernameHolder {
    private String username;
    private final static UsernameHolder instance = new UsernameHolder();
    private UsernameHolder() {}

    public static UsernameHolder getInstance() {
        return instance;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}
