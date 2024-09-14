package com.chatbox.entity.component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupDetailId implements Serializable {
    private String idGroup;
    private String idUser;
}
