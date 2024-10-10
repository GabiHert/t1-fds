package com.fds.siscaa.interfaceAdapters.repository.model;

import jakarta.persistence.Id;

public class UserModel {
    @Id
    public String username;
    public String password;

    protected UserModel() {
    }

    public UserModel(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
