package com.chatbox.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tUser")
public class User {
    @Id
    private String id;

    @Column(name = "fullname", columnDefinition = "NVARCHAR2(255)")
    private String fullname;

    @Column(name = "status")
    private String status;
}
