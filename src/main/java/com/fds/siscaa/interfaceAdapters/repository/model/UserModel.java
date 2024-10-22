package com.fds.siscaa.interfaceAdapters.repository.model;

import com.fds.siscaa.domain.entity.UserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity()
@Table(name = "'User'")
@Getter()
@Setter()
@AllArgsConstructor()
public class UserModel {
    @Id

    @Column(name = "username")
    private String username;

    @Column(name = "password")
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
