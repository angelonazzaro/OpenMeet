package com.openmeet.data.rating

import com.openmeet.shared.data.storage.DAO
import com.openmeet.shared.data.storage.SQLDAO
import java.util.HashMap
import javax.sql.DataSource

class RatingDAO(source: DataSource) : SQLDAO(source), DAO<Rating> {

    override fun doRetrieveByCondition(condition: String?): MutableList<Rating> {
        return genericDoRetrieveByCondition(
            Rating.RATING,
            condition,
            RatingExtractor(),
            source
        )
    }

    override fun doRetrieveByKey(key: String?): Rating? {

        if (key == null) {
            throw IllegalArgumentException("null")
        }

        val rating = doRetrieveByCondition("$Rating.RATING_ID = '$key'")

        return if (rating.isEmpty()) null else rating[0]
    }

    override fun doRetrieveAll(): MutableList<Rating> {
        return doRetrieveByCondition("TRUE")
    }

    override fun doRetrieveAll(row_count: Int): MutableList<Rating> {
        return doRetrieveByCondition("TRUE LIMIT $row_count")
    }

    override fun doRetrieveAll(offset: Int, row_count: Int): MutableList<Rating> {
        return doRetrieveByCondition("TRUE LIMIT $offset, $row_count");
    }

    override fun doSave(obj: Rating?): Boolean {
        return genericDoSave(Rating.RATING, obj?.toHashMap(), source)
    }

    override fun doSave(values: HashMap<String, *>?): Boolean {
        return genericDoSave(Rating.RATING, values, source)
    }

    override fun doUpdate(values: HashMap<String, *>?, condition: String?): Boolean {
        return genericDoUpdate(Rating.RATING, condition, values, source)
    }

    override fun doSaveOrUpdate(obj: Rating?): Boolean {

        if (doRetrieveByKey(obj?.id.toString()) == null) {
            return doSave(obj)
        }
        return doUpdate(obj?.toHashMap(), "$Rating.RATING_ID = '${obj?.id}'")

    }

    override fun doDelete(condition: String?): Boolean {
        return genericDoDelete(Rating.RATING, condition, source)
    }
}