package com.chatbox.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "tNotification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "sender")
    private String sender;

    @Column(name = "content", columnDefinition = "NCLOB")
    private String content;

    @Column(name = "received")
    private String received;

    @Column(name = "sendTime")
    private Timestamp sendTime;

    @Column(name = "idChatRoom")
    private String idChatRoom;

    @Column(name = "type")
    private int type;

    @Column(name = "isRead")
    private int isRead;

    @Column(name = "isEnable")
    private int isEnable;

    @Column(name = "isAgreed")
    private Integer isAgreed;
}
