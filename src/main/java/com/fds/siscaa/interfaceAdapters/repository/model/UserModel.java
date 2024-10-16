package com.fds.siscaa.interfaceAdapters.repository.model;

import com.fds.siscaa.domain.entity.UserEntity;

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

    public UserModel(UserEntity userEntity) {
        this.username = userEntity.username;
        this.password = userEntity.password;
    }

    public UserEntity toEntity() {
        return new UserEntity(username, password);
    }
}
