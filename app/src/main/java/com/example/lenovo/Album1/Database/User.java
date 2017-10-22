package com.example.lenovo.Album1.Activity.recyclerview.Database;

/**
 * Created by LENOVO on 9/21/2017.
 */

public class User {

    private String username;
    private String user_id;
    private String message;
    private String email;
    private String date;
    public String getUser_ID() {
        return user_id;
    }
    public void setUser_ID(String movie_id) {
        this.user_id = movie_id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email =email;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
}
