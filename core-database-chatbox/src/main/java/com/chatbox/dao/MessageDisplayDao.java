package com.chatbox.dao;

import com.chatbox.entity.MessageDisplay;
import com.chatbox.mapper.MessageDisplayMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class MessageDisplayDao extends AbstractDao<MessageDisplay> {
    public MessageDisplayDao(DataSource dataSource) {
        super(dataSource);
    }

    public List<MessageDisplay> getMessage(String code, String idUser, int index) {
        String procName = "{call proc_SelectMessageDisPlay(?, ?, ?, ?)}";
        List<MessageDisplay> messages = query(procName, new MessageDisplayMapper(), code, idUser, index);
        return messages;
    }
}
