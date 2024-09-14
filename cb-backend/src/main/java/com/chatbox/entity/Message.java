package com.chatbox.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tMessage")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "sender")
    private String sender;

    @Column(name = "received")
    private String received;

    @Column(name = "content", columnDefinition = "NCLOB")
    private String content;

    @Column(name = "code")
    private String code;

    @Column(name = "sendTime")
    private Timestamp sendTime;

    @Column(name = "isEnable")
    private int isEnable;

    @Column(name = "type")
    private int type;

    @Column(name = "messageEdt")
    private Long messageEdt;
}
