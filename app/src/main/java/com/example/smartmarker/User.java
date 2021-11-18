package com.example.smartmarker;

public class User {

    private String id, pw, name, token;

    public User(){

    }

    public User(String id, String pw, String name,  String token) {
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.token = token;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
