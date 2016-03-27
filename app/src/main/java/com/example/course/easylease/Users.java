package com.example.course.easylease;

public class Users {
    private String User_Name;
    private String Password;
    private String Email;
    private String Phone_Number;

    public Users(String username, String password, String email, String phoneNumber) {
        this.User_Name = username;
        this.Password = password;
        this.Email = email;
        this.Phone_Number = phoneNumber;
    }

    public Users(String username, String password, String email) {
        this.User_Name = username;
        this.Password = password;
        this.Email = email;
    }
}
