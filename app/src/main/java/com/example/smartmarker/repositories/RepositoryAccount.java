package com.example.smartmarker.repositories;

public class RepositoryAccount {
    private static RepositoryAccount repositoryAccount = new RepositoryAccount();

    private String id, time, location;

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
}
