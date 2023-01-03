package com.openmeet.data.interest

import com.openmeet.shared.data.storage.DAO
import com.openmeet.shared.data.storage.SQLDAO
import java.util.HashMap
import javax.sql.DataSource

class InterestDAO(source: DataSource) : SQLDAO(source), DAO<Interest> {

    override fun doRetrieveByCondition(condition: String?): MutableList<Interest> {
        return genericDoRetrieveByCondition(
            Interest.INTEREST,
            condition,
            InterestExtractor(),
            source
        )
    }

    override fun doRetrieveByKey(key: String?): Interest? {

        if (key == null) {
            throw IllegalArgumentException("null")
        }

        val interest = doRetrieveByCondition("${Interest.INTEREST}.id = '$key'")

        return if (interest.isEmpty()) null else interest[0]
    }

    override fun doRetrieveAll(): MutableList<Interest> {
        return doRetrieveByCondition("TRUE")
    }

    override fun doRetrieveAll(row_count: Int): MutableList<Interest> {
        return doRetrieveByCondition("TRUE LIMIT $row_count")
    }

    override fun doRetrieveAll(offset: Int, row_count: Int): MutableList<Interest> {
        return doRetrieveByCondition("TRUE LIMIT $offset, $row_count");
    }

    override fun doSave(obj: Interest?): Boolean {
        return genericDoSave(Interest.INTEREST, obj?.toHashMap(), source)
    }

    override fun doUpdate(values: HashMap<String, *>?, condition: String?): Boolean {
        return genericDoUpdate(Interest.INTEREST, condition, values, source)
    }

    override fun doSaveOrUpdate(obj: Interest?): Boolean {

        if (doRetrieveByKey(obj?.id.toString()) == null) {
            return doSave(obj)
        }
        return doUpdate(obj?.toHashMap(), "${Interest.INTEREST}.id = '${obj?.id}'")

    }

    override fun doDelete(condition: String?): Boolean {
        return genericDoDelete(Interest.INTEREST, condition, source)
    }
}