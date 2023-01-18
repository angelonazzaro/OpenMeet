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
        meeter.setBiography(resultSet.getString(Meeter.MEETER_BIOGRAPHY));
        meeter.setBirthDate(resultSet.getDate(Meeter.MEETER_BIRTH_DATE));
        meeter.setPublicKey(resultSet.getBytes(Meeter.MEETER_PUBLIC_KEY));

        return meeter;
    }
}

