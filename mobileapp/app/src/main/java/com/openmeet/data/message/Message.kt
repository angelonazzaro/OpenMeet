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
}