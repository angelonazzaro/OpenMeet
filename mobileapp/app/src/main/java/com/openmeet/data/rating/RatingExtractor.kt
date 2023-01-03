package com.openmeet.data.rating

import com.openmeet.shared.data.storage.ResultSetExtractor
import java.sql.ResultSet

class RatingExtractor : ResultSetExtractor<Rating> {

    override fun extract(resultSet: ResultSet): Rating {

        val rating = Rating()

        rating.id = resultSet.getInt(Rating.RATING + ".id")
        rating.meeterRater = resultSet.getInt(Rating.RATING + ".meeterRater")
        rating.meeterRated = resultSet.getInt(Rating.RATING + ".meeterRated")
        rating.type = resultSet.getBoolean(Rating.RATING + ".type")
        rating.creationDate = resultSet.getTimestamp(Rating.RATING + ".creationDate")

        return rating
    }
}
