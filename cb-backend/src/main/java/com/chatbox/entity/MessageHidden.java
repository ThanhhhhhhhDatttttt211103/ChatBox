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
@Table(name = "tMessageHidden")
public class MessageHidden {
    @Id
    @Column(name = "idMess")
    private String idMess;
    @Id
    @Column(name = "idUser")
    private String idUser;
}
