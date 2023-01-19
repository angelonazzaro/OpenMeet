package com.openmeet.shared.data.rating;

import com.openmeet.shared.data.storage.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RatingExtractor implements ResultSetExtractor<Rating> {

    @Override
    public Rating extract(ResultSet resultSet) throws SQLException {

        Rating rating = new Rating();

        rating.setId(resultSet.getInt(Rating.RATING_ID));
        rating.setMeeterRater(resultSet.getInt(Rating.RATING_MEETER_RATER));
        rating.setMeeterRated(resultSet.getInt(Rating.RATING_MEETER_RATED));
        rating.setType(resultSet.getBoolean(Rating.RATING_TYPE));
        rating.setCreationDate(resultSet.getTimestamp(Rating.RATING_CREATION_DATE));

        return rating;
    }
}
