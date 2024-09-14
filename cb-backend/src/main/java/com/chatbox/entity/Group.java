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
@Table(name = "tGroup")
public class Group {
    @Id
    private String id;

    @Column(name = "groupName", columnDefinition = "NVARCHAR2(255)")
    private String groupName;
}
