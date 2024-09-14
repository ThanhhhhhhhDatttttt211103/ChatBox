package com.chatbox.entity;

import com.chatbox.entity.component.FriendId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tFriend")
@IdClass(FriendId.class)
public class Friend {
    @Id
    @Column(name = "idUser")
    private String idUser;

    @Id
    @Column(name = "friend")
    private String friend;

    @Column(name = "code")
    private String code;

    @Column(name = "isEnable")
    private int isEnable;
}
