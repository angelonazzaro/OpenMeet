package com.openmeet.data.message

import com.openmeet.shared.data.storage.ResultSetExtractor
import java.sql.ResultSet

class MessageExtractor : ResultSetExtractor<Message> {

    override fun extract(resultSet: ResultSet): Message {

        val message = Message()

        message.id = resultSet.getInt(Message.MESSAGE + ".id")
        message.text = resultSet.getString(Message.MESSAGE + ".text")
        message.sentTime = resultSet.getTimestamp(Message.MESSAGE + ".sentTime")
        message.deliveredTime = resultSet.getTimestamp(Message.MESSAGE + ".deliveredTime")
        message.readTime = resultSet.getTimestamp(Message.MESSAGE + ".readTime")
        message.meeterSender = resultSet.getInt(Message.MESSAGE + ".meeterSender")
        message.meeterReceiver = resultSet.getInt(Message.MESSAGE + ".meeterReceiver")

        return message
    }
}