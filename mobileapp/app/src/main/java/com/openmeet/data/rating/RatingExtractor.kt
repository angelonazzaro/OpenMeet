package com.openmeet.data.rating

import com.openmeet.shared.data.storage.ResultSetExtractor
import java.sql.ResultSet

class RatingExtractor : ResultSetExtractor<Rating> {

    override fun extract(resultSet: ResultSet): Rating {

        val rating = Rating()

        rating.id = resultSet.getInt(Rating.RATING_ID)
        rating.meeterRater = resultSet.getInt(Rating.RATING_MEETER_RATER)
        rating.meeterRated = resultSet.getInt(Rating.RATING_MEETER_RATED)
        rating.type = resultSet.getBoolean(Rating.RATING_TYPE)
        rating.creationDate = resultSet.getTimestamp(Rating.RATING_CREATION_DATE)

        return rating
    }
}
