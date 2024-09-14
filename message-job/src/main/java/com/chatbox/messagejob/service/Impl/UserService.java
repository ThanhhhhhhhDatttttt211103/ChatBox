package com.chatbox.messagejob.service.Impl;

import com.chatbox.messagejob.entity.User;
import com.chatbox.messagejob.repository.UserRepository;
import com.chatbox.messagejob.service.IUserService;
import com.chatbox.messagejob.util.MessageUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserService implements IUserService {

    private UserRepository userRepository;

    @Override
    public String getNameUser(String idUser) {
        try {
            log.info("Begin getNameUser with id = {}", idUser);
            User user = userRepository.findUserById(idUser);
            if (user == null) {
                log.info("Can't find user");
                return MessageUtil.ISEMPTY;
            }
            log.info("End getNameUser SUCCESS with fullName = {}", user.getFullname());
            return user.getFullname();
        } catch (Exception e) {
            log.info("Exception in getNameUser");
            log.error("Exception in getNameUser",e);
            return MessageUtil.ISEMPTY;
        }
    }
}
