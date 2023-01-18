package com.openmeet.data.rating

import com.openmeet.shared.data.storage.IEntity
import java.sql.Timestamp
import java.util.HashMap

data class Rating(
    var id: Int = 0,
    var meeterRater: Int = 0,
    var meeterRated: Int = 0,
    var type: Boolean = false,
    var creationDate: Timestamp? = null
) : IEntity {

    companion object {
        const val RATING = "Rating"
        const val RATING_ID = "$RATING.id"
        const val RATING_MEETER_RATER = "$RATING.meeterRater"
        const val RATING_MEETER_RATED = "$RATING.meeterRated"
        const val RATING_TYPE = "$RATING.type"
        const val RATING_CREATION_DATE = "$RATING.creationDate"
    }

    override fun toHashMap(): HashMap<String, *> {

        val map = HashMap<String, Any>()

        map["id"] = id
        map["meeterRater"] = meeterRater
        map["meeterRated"] = meeterRated
        map["type"] = type
        map["creationDate"] = creationDate!!

        return map
    }

    override fun toHashMap(vararg fields: String?): HashMap<String, *> {

        val map = HashMap<String, Any>()
        for (field in fields) {
            when (field) {
                RATING_ID -> map["id"] = id
                RATING_MEETER_RATER -> map["meeterRater"] = meeterRater
                RATING_MEETER_RATED -> map["meeterRated"] = meeterRated
                RATING_TYPE -> map["type"] = type
                RATING_CREATION_DATE -> map["creationDate"] = creationDate!!
            }
        }
        return map
    }
}