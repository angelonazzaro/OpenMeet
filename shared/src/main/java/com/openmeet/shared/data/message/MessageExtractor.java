package com.openmeet.shared.data.message;

import com.openmeet.shared.data.storage.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MessageExtractor implements ResultSetExtractor<Message> {

    @Override
    public Message extract(ResultSet resultSet) throws SQLException {

        Message message = new Message();

        message.setId(resultSet.getInt(Message.MESSAGE_ID));
        message.setText(resultSet.getString(Message.MESSAGE_TEXT));
        message.setSentTime(resultSet.getTimestamp(Message.MESSAGE_SENT_TIME));
        message.setDeliveredTime(resultSet.getTimestamp(Message.MESSAGE_DELIVERED_TIME));
        message.setReadTime(resultSet.getTimestamp(Message.MESSAGE_READ_TIME));
        message.setMeeterSender(resultSet.getInt(Message.MESSAGE_MEETER_SENDER));
        message.setMeeterReceiver(resultSet.getInt(Message.MESSAGE_MEETER_RECEIVER));

        return message;
    }
}
