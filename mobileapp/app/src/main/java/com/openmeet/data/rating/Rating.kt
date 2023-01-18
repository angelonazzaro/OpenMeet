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
        map["meeter_rater"] = meeterRater
        map["meeter_rated"] = meeterRated
        map["type"] = type
        map["creation_date"] = creationDate!!

        return map
    }
}