package com.chatbox.service.impl;

import com.chatbox.constant.Status;
import com.chatbox.dto.UserDto;
import com.chatbox.entity.User;
import com.chatbox.mapper.UserMapper;
import com.chatbox.repository.UserRepository;
import com.chatbox.service.IUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
@Slf4j
public class UserService implements IUserService {
    private UserRepository userRepository;

    @Override
    public UserDto findUserById(String idUser) {
        try {
            log.info("Begin findUserById with idUser = {}", idUser);
            User user = userRepository.findUserById(idUser);
            if (user == null) {
                log.info("Can't find any user");
                return new UserDto();
            }
            log.info("End findUserById with user = {}", user);
            return UserMapper.mapToUserDto(user);
        } catch (Exception e) {
            log.info("Exception in findUserById");
            log.error("Exception in findUserById", e);
            return new UserDto();
        }
    }

    @Override
    public void connectUser(UserDto userDto) {
        userDto.setStatus(Status.ONLINE);
        User user = UserMapper.mapToUser(userDto);
        userRepository.save(user);
    }

    @Override
    public void disconnectUser(UserDto userDto) {
        User storedUser = userRepository.findById(userDto.getId()).orElse(null);
        if (storedUser != null) {
            storedUser.setStatus(Status.OFFLINE);
            userRepository.save(storedUser);
        }
    }
}
