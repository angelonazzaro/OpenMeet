package com.openmeet.shared.data.meeter;

import com.openmeet.shared.data.storage.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The extractor is used to convert the ResultSet returned by the database query into a Meeter object.
 *
 * @author Angelo Nazzaro
 * @author Francesco Granozio
 * @see ResultSetExtractor
 */
public class MeeterExtractor implements ResultSetExtractor<Meeter> {

    /**
     * Returns a Meeter object from the result set.
     *
     * @param resultSet the result set deriving from the query statement.
     * @return the Meeter object contained in the resultSet.
     * @author Francesco Granozio
     * @author Angelo Nazzaro
     */
    @Override
    public Meeter extract(ResultSet resultSet) throws SQLException {

        Meeter meeter = new Meeter();

        meeter.setId(resultSet.getInt(Meeter.MEETER_ID));
        meeter.setEmail(resultSet.getString(Meeter.MEETER_EMAIL));
        meeter.setMeeterName(resultSet.getString(Meeter.MEETER_MEETER_NAME));
        meeter.setMeeterSurname(resultSet.getString(Meeter.MEETER_MEETER_SURNAME));
        meeter.setPwd(resultSet.getString(Meeter.MEETER_PWD), false);
        meeter.setGender(resultSet.getString(Meeter.MEETER_GENDER));
        meeter.setSearchingGender(resultSet.getString(Meeter.MEETER_SEARCHING_GENDER));
        meeter.setCity(resultSet.getString(Meeter.MEETER_CITY));
        meeter.setBiography(resultSet.getString(Meeter.MEETER_BIOGRAPHY));
        meeter.setBirthdate(resultSet.getDate(Meeter.MEETER_BIRTHDATE));
        meeter.setPublicKey(resultSet.getString(Meeter.MEETER_PUBLIC_KEY));

        return meeter;
    }
}

