package com.example.smartmarker.repositories;

public class RepositoryAccount {
    private static RepositoryAccount repositoryAccount = new RepositoryAccount();

    private String id, time, location, care_id, range;
    private String phone;
    private Double lati, longi;

    public static RepositoryAccount getInstance() {
        return repositoryAccount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }
    public void setTime(String time)
    {
        this.time=time;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location)
    {
        this.location=location;
    }

    public Double getLati() { return lati; }
    public void setLati(Double lati)
    {
        this.lati=lati;
    }

    public Double getLongi() {
        return longi;
    }
    public void setLongi(Double longi)
    {
        this.longi=longi;
    }

    public String getCare_id() {
        return care_id;
    }

    public void setCare_id(String care_id) {
        this.care_id = care_id;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
