package com.chatbox.messagejob.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tUser")
@ToString
public class User {
    @Id
    private String id;

    @Column(name = "fullname", columnDefinition = "NVARCHAR2(255)")
    private String fullname;

    @Column(name = "status")
    private String status;
}
