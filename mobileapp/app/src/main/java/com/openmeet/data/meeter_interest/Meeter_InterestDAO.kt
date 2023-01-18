package com.openmeet.data.meeter_interest

import com.openmeet.shared.data.storage.DAO
import com.openmeet.shared.data.storage.SQLDAO
import java.util.HashMap
import javax.sql.DataSource

class Meeter_InterestDAO(source: DataSource) : SQLDAO(source), DAO<Meeter_Interest> {

    override fun doRetrieveByCondition(condition: String?): MutableList<Meeter_Interest> {
        return genericDoRetrieveByCondition(
            Meeter_Interest.MEETER_INTEREST,
            condition,
            Meeter_InterestExtractor(),
            source
        )
    }

    override fun doRetrieveByKey(key: String?): Meeter_Interest? {

        if (key == null) {
            throw IllegalArgumentException("null")
        }

        val meeterInterest = doRetrieveByCondition("$Meeter_Interest.MEETER_INTEREST_MEETER_ID = '$key'")

        return if (meeterInterest.isEmpty()) null else meeterInterest[0]
    }

    override fun doRetrieveAll(): MutableList<Meeter_Interest> {
        return doRetrieveByCondition("TRUE")
    }

    override fun doRetrieveAll(row_count: Int): MutableList<Meeter_Interest> {
        return doRetrieveByCondition("TRUE LIMIT $row_count")
    }

    override fun doRetrieveAll(offset: Int, row_count: Int): MutableList<Meeter_Interest> {
        return doRetrieveByCondition("TRUE LIMIT $offset, $row_count");
    }

    override fun doSave(obj: Meeter_Interest?): Boolean {
        return genericDoSave(Meeter_Interest.MEETER_INTEREST, obj?.toHashMap(), source)
    }

    override fun doSave(values: HashMap<String, *>?): Boolean {
        return genericDoSave(Meeter_Interest.MEETER_INTEREST, values, source)
    }

    override fun doUpdate(values: HashMap<String, *>?, condition: String?): Boolean {
        return genericDoUpdate(Meeter_Interest.MEETER_INTEREST, condition, values, source)
    }

    override fun doSaveOrUpdate(obj: Meeter_Interest?): Boolean {

        if (doRetrieveByKey(obj?.id.toString()) == null) {
            return doSave(obj)
        }
        return doUpdate(obj?.toHashMap(), "$Meeter_Interest.MEETER_INTEREST_MEETER_ID = '${obj?.id}'")
    }

    override fun doDelete(condition: String?): Boolean {
        return genericDoDelete(Meeter_Interest.MEETER_INTEREST, condition, source)
    }
}