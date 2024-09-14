package com.chatbox.entity;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupCore {
    private String id;
    private String groupName;
}
