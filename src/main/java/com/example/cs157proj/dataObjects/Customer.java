package com.example.cs157proj.dataObjects;

public class Customer {
    private String username;
    private String password;
    private String name;
    private int age;

    // Constructor
    public Customer(String username, String password, String name, int age) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.age = age;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
