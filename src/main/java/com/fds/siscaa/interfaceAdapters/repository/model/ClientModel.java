package com.fds.siscaa.interfaceAdapters.repository.model;

import com.fds.siscaa.domain.entity.ClientEntity;

import com.fds.siscaa.domain.utils.CustomList;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity()
@Table(name = "Client")
@Getter()
@Setter()
@AllArgsConstructor()
public class ClientModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long code;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    protected ClientModel() {
    }

    public ClientModel(ClientEntity clientEntity) {
        this.code = clientEntity.getCode();
        this.name = clientEntity.getName();
        this.email = clientEntity.getEmail();
    }

    public ClientEntity toEntity() {
        return new ClientEntity(code, name, email);
    }
}
