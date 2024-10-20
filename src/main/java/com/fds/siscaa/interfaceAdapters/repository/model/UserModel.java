package com.fds.siscaa.interfaceAdapters.repository.model;

import com.fds.siscaa.domain.entity.UserEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity()
@Getter()
@Setter()
@AllArgsConstructor()
public class UserModel {
    @Id
    private String username;
    private String password;

    protected UserModel() {
    }

    public UserModel(UserEntity userEntity) {
        this.username = userEntity.getUsername();
        this.password = userEntity.getPassword();
    }

    public UserEntity toEntity() {
        return new UserEntity(username, password);
    }
}
