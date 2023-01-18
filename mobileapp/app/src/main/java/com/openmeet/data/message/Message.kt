package com.openmeet.data.message

import com.openmeet.shared.data.storage.IEntity
import java.sql.Timestamp
import java.util.HashMap

data class Message(
    var id: Int = 0,
    var text: String? = null,
    var sentTime: Timestamp? = null,
    var deliveredTime: Timestamp? = null,
    var readTime: Timestamp? = null,
    var meeterSender: Int = 0,
    var meeterReceiver: Int = 0
) : IEntity {

    companion object {
        const val MESSAGE = "Message"
        const val MESSAGE_ID = "$MESSAGE.id"
        const val MESSAGE_TEXT = "$MESSAGE.text"
        const val MESSAGE_SENT_TIME = "$MESSAGE.sentTime"
        const val MESSAGE_DELIVERED_TIME = "$MESSAGE.deliveredTime"
        const val MESSAGE_READ_TIME = "$MESSAGE.readTime"
        const val MESSAGE_MEETER_SENDER = "$MESSAGE.meeterSender"
        const val MESSAGE_MEETER_RECEIVER = "$MESSAGE.meeterReceiver"
    }

    override fun toHashMap(): HashMap<String, *> {

        val map = HashMap<String, Any>()

        map["id"] = id
        map["text"] = text!!
        map["sentTime"] = sentTime!!
        map["deliveredTime"] = deliveredTime!!
        map["readTime"] = readTime!!
        map["meeterSender"] = meeterSender
        map["meeterReceiver"] = meeterReceiver

        return map
    }

    override fun toHashMap(vararg fields: String?): HashMap<String, *> {

        val map = HashMap<String, Any>()
        for (field in fields) {
            when (field) {
                MESSAGE_ID -> map["id"] = id
                MESSAGE_TEXT -> map["text"] = text!!
                MESSAGE_SENT_TIME -> map["sentTime"] = sentTime!!
                MESSAGE_DELIVERED_TIME -> map["deliveredTime"] = deliveredTime!!
                MESSAGE_READ_TIME -> map["readTime"] = readTime!!
                MESSAGE_MEETER_SENDER -> map["meeterSender"] = meeterSender
                MESSAGE_MEETER_RECEIVER -> map["meeterReceiver"] = meeterReceiver
            }
        }
        return map
    }
}