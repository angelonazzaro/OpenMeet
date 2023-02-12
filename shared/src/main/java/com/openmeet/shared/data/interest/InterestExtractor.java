package com.openmeet.shared.data.interest;

import com.openmeet.shared.data.storage.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The extractor is used to convert the ResultSet returned by the database query into an Interest object.
 *
 * @see ResultSetExtractor
 *
 * @author Angelo Nazzaro
 * @author Francesco Granozio
 */
public class InterestExtractor implements ResultSetExtractor<Interest> {

    /**
     * Returns an Interest object from the result set.
     *
     * @param resultSet the result set deriving from the query statement.
     * @return the Interest object contained in the resultSet.
     *
     * @author Francesco Granozio
     * @author Angelo Nazzaro
     */
    @Override
    public Interest extract(ResultSet resultSet) throws SQLException {

        Interest interest = new Interest();

        interest.setId(resultSet.getInt(Interest.INTEREST_ID));
        interest.setDescription(resultSet.getString(Interest.INTEREST_DESCRIPTION));

        return interest;
    }
}