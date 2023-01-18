package com.openmeet.data.meeter_interest

import com.openmeet.shared.data.storage.ResultSetExtractor
import java.sql.ResultSet

class Meeter_InterestExtractor : ResultSetExtractor<Meeter_Interest> {

    override fun extract(resultSet: ResultSet): Meeter_Interest {

        val meeterInterest = Meeter_Interest()

        meeterInterest.id = resultSet.getInt(Meeter_Interest.MEETER_INTEREST_ID)
        meeterInterest.meeterId = resultSet.getInt(Meeter_Interest.MEETER_INTEREST_MEETER_ID)

        return meeterInterest
    }
}
