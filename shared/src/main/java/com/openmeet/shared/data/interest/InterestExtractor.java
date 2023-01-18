package com.openmeet.shared.data.interest;

import com.openmeet.shared.data.storage.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InterestExtractor implements ResultSetExtractor<Interest> {

    @Override
    public Interest extract(ResultSet resultSet) throws SQLException {

        Interest interest = new Interest();

        interest.setId(resultSet.getInt(Interest.INTEREST_ID));
        interest.setDescription(resultSet.getString(Interest.INTEREST_DESCRIPTION));

        return interest;
    }
}