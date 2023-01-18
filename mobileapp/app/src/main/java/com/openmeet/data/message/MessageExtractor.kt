package com.openmeet.data.message

import com.openmeet.shared.data.storage.ResultSetExtractor
import java.sql.ResultSet

class MessageExtractor : ResultSetExtractor<Message> {

    override fun extract(resultSet: ResultSet): Message {

        val message = Message()

        message.id = resultSet.getInt(Message.MESSAGE_ID)
        message.text = resultSet.getString(Message.MESSAGE_TEXT)
        message.sentTime = resultSet.getTimestamp(Message.MESSAGE_SENT_TIME)
        message.deliveredTime = resultSet.getTimestamp(Message.MESSAGE_DELIVERED_TIME)
        message.readTime = resultSet.getTimestamp(Message.MESSAGE_READ_TIME)
        message.meeterSender = resultSet.getInt(Message.MESSAGE_MEETER_SENDER)
        message.meeterReceiver = resultSet.getInt(Message.MESSAGE_MEETER_RECEIVER)

        return message
    }
}