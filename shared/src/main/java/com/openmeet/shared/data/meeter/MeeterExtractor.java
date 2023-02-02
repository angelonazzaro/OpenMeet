package com.openmeet.shared.data.meeter;

import com.openmeet.shared.data.storage.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MeeterExtractor implements ResultSetExtractor<Meeter> {
    @Override
    public Meeter extract(ResultSet resultSet) throws SQLException {

        Meeter meeter = new Meeter();

        meeter.setId(resultSet.getInt(Meeter.MEETER_ID));
        meeter.setEmail(resultSet.getString(Meeter.MEETER_EMAIL));
        meeter.setMeeterName(resultSet.getString(Meeter.MEETER_MEETER_NAME));
        meeter.setMeeterSurname(resultSet.getString(Meeter.MEETER_MEETER_SURNAME));
        meeter.setPwd(resultSet.getString(Meeter.MEETER_PWD));
        meeter.setGender(resultSet.getString(Meeter.MEETER_GENDER).charAt(0));
        meeter.setSearchingGender(resultSet.getString(Meeter.MEETER_SEARCHING_GENDER).charAt(0));
        meeter.setCity(resultSet.getString(Meeter.MEETER_CITY));
        meeter.setBiography(resultSet.getString(Meeter.MEETER_BIOGRAPHY));
        meeter.setBirthdate(resultSet.getDate(Meeter.MEETER_BIRTHDATE));
        meeter.setPublicKey(resultSet.getString(Meeter.MEETER_PUBLIC_KEY));

        return meeter;
    }
}

