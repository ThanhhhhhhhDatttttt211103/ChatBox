package com.chatbox.messagejob.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tGroup")
@ToString
public class Group {
    @Id
    private String id;

    @Column(name = "groupName", columnDefinition = "NVARCHAR2(255)")
    private String groupName;
}
