package com.openmeet.data.message

import com.openmeet.shared.data.storage.DAO
import com.openmeet.shared.data.storage.SQLDAO
import java.util.HashMap
import javax.sql.DataSource

class MessageDAO(source: DataSource) : SQLDAO(source), DAO<Message> {

    override fun doRetrieveByCondition(condition: String?): MutableList<Message> {
        return genericDoRetrieveByCondition(
            Message.MESSAGE,
            condition,
            MessageExtractor(),
            source
        )
    }

    override fun doRetrieveByKey(key: String?): Message? {

        if (key == null) {
            throw IllegalArgumentException("null")
        }

        val message = doRetrieveByCondition("${Message.MESSAGE}.id = '$key'")

        return if (message.isEmpty()) null else message[0]
    }

    override fun doRetrieveAll(): MutableList<Message> {
        return doRetrieveByCondition("TRUE")
    }

    override fun doRetrieveAll(row_count: Int): MutableList<Message> {
        return doRetrieveByCondition("TRUE LIMIT $row_count")
    }

    override fun doRetrieveAll(offset: Int, row_count: Int): MutableList<Message> {
        return doRetrieveByCondition("TRUE LIMIT $offset, $row_count");
    }

    override fun doSave(obj: Message?): Boolean {
        return genericDoSave(Message.MESSAGE, obj?.toHashMap(), source)
    }

    override fun doUpdate(values: HashMap<String, *>?, condition: String?): Boolean {
        return genericDoUpdate(Message.MESSAGE, condition, values, source)
    }

    override fun doSaveOrUpdate(obj: Message?): Boolean {

        if (doRetrieveByKey(obj?.id.toString()) == null) {
            return doSave(obj)
        }
        return doUpdate(obj?.toHashMap(), "${Message.MESSAGE}.id = '${obj?.id}'")
    }

    override fun doDelete(condition: String?): Boolean {
        return genericDoDelete(Message.MESSAGE, condition, source)
    }
}