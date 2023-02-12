package com.openmeet.shared.data.meeter_interest;

import com.openmeet.shared.data.storage.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The extractor is used to convert the ResultSet returned by the database query into a Meeter_Interest object.
 *
 * @author Angelo Nazzaro
 * @author Francesco Granozio
 */
public class Meeter_InterestExtractor implements ResultSetExtractor<Meeter_Interest> {

    /**
     * Returns a Meeter_Interest object from the result set.
     *
     * @param resultSet the result set deriving from the query statement.
     * @return the Meeter_Interest object contained in the resultSet.
     *
     * @author Francesco Granozio
     * @author Angelo Nazzaro
     */
    @Override
    public Meeter_Interest extract(ResultSet resultSet) throws SQLException {

        Meeter_Interest meeterInterest = new Meeter_Interest();

        meeterInterest.setId(resultSet.getInt(Meeter_Interest.MEETER_INTEREST_ID));
        meeterInterest.setInterestId(resultSet.getInt(Meeter_Interest.MEETER_INTEREST_INTEREST_ID));
        meeterInterest.setMeeterId(resultSet.getInt(Meeter_Interest.MEETER_INTEREST_MEETER_ID));

        return meeterInterest;
    }
}
