package com.example.smartmarker;

public class User {

    private String id, pw, name, phone, home, token;
    public User(){

    }

    public User(String id, String pw, String name, String phone, String home, String token) {
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.phone= phone;
        this.home=home;
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

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
