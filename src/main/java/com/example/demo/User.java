package com.example.demo;

public class User {
    String firstName;
    String lastName;
    String email;
    String password;
    String userName;
    String shippingAddress;
    String phone;
    Boolean manager;
    public User(String firstName,String lastName,String userName,String email,String password,String phone,String shippingAddress,Boolean manager){
        this.firstName=firstName;
        this.lastName=lastName;
        this.userName=userName;
        this.password=password;
        this.phone=phone;
        this.manager=manager;
        this.shippingAddress=shippingAddress;
        this.email=email;
    }
}
