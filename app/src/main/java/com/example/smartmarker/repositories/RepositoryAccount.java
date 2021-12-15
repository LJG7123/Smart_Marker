package com.example.smartmarker.repositories;

public class RepositoryAccount {
    private static RepositoryAccount repositoryAccount = new RepositoryAccount();

    private String id;

    public static RepositoryAccount getInstance() {
        return repositoryAccount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
