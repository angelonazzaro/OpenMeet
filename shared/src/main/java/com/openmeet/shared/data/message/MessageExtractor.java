package com.openmeet.shared.data.message;

import com.openmeet.shared.data.storage.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The extractor is used to convert the ResultSet returned by the database query into a Message object.
 *
 * @author Angelo Nazzaro
 * @author Francesco Granozio
 * @see ResultSetExtractor
 */
public class MessageExtractor implements ResultSetExtractor<Message> {

    /**
     * Returns a Message object from the result set.
     *
     * @param resultSet the result set deriving from the query statement.
     * @return the Message object contained in the resultSet.
     * @author Francesco Granozio
     * @author Angelo Nazzaro
     */
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
