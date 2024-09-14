package com.chatbox.messagejob.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserDto {
    private String id;
    private String fullname;
    private String status;
}
