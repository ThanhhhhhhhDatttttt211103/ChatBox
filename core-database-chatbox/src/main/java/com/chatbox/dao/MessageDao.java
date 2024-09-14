package com.chatbox.dao;

import com.chatbox.entity.MessageCore;
import com.chatbox.mapper.MessageCoreMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class MessageDao extends AbstractDao<MessageCore> {
    public MessageDao(DataSource dataSource) {
        super(dataSource);
    }

    public List<MessageCore> getMessage(String code, String idUser, int index) {
        String procName = "{call proc_SelectMessage(?, ?, ?, ?)}";
        List<MessageCore> messages = query(procName, new MessageCoreMapper(), code, idUser, index);
        return messages;
    }
}
