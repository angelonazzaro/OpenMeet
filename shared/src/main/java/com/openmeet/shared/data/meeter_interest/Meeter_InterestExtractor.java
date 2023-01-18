package com.openmeet.shared.data.meeter_interest;

import com.openmeet.shared.data.storage.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Meeter_InterestExtractor implements ResultSetExtractor<Meeter_Interest> {

    @Override
    public Meeter_Interest extract(ResultSet resultSet) throws SQLException {

        Meeter_Interest meeterInterest = new Meeter_Interest();

        meeterInterest.setId(resultSet.getInt(Meeter_Interest.MEETER_INTEREST_ID));
        meeterInterest.setMeeterId(resultSet.getInt(Meeter_Interest.MEETER_INTEREST_MEETER_ID));

        return meeterInterest;
    }
}
