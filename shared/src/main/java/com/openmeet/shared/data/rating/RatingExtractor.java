package com.openmeet.shared.data.rating;

import com.openmeet.shared.data.storage.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The extractor is used to convert the ResultSet returned by the database query into a Rating object.
 *
 * @see ResultSetExtractor
 *
 * @author Angelo Nazzaro
 * @author Francesco Granozio
 */
public class RatingExtractor implements ResultSetExtractor<Rating> {

    /**
     * Returns a Rating object from the result set.
     *
     * @param resultSet the result set deriving from the query statement.
     * @return the Rating object contained in the resultSet.
     *
     * @author Francesco Granozio
     * @author Angelo Nazzaro
     */
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
