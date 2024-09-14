package com.chatbox.entity;

import com.chatbox.entity.component.GroupDetailId;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "tGroupDetail")
@IdClass(GroupDetailId.class)
public class GroupDetail {
    @Id
    @Column(name = "idGroup")
    private String idGroup;

    @Id
    @Column(name = "idUser")
    private String idUser;

    @Column(name = "isEnable")
    private int isEnable;
}
