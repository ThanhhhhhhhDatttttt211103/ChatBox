package com.chatbox.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tAccount")
public class Account {
    @Id
    @Column(name = "idUser")
    private String idUser;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;
}
