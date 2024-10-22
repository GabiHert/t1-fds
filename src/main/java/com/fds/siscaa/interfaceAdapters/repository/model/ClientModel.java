package com.fds.siscaa.interfaceAdapters.repository.model;

import com.fds.siscaa.domain.entity.ClientEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Entity()
@Table(name = "client")
@Getter()
@Setter()
@AllArgsConstructor()
public class ClientModel {
    @Id
    private long code;
    private String name;
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
