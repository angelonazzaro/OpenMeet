package com.openmeet.shared.data.meeter;

import com.openmeet.shared.data.storage.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MeeterExtractor implements ResultSetExtractor<Meeter> {
    @Override
    public Meeter extract(ResultSet resultSet) throws SQLException {

        Meeter meeter = new Meeter();

        meeter.setId(resultSet.getInt(Meeter.MEETER + ".id"));
        meeter.setEmail(resultSet.getString(Meeter.MEETER + ".email"));
        meeter.setMeeterName(resultSet.getString(Meeter.MEETER + ".meeterName"));
        meeter.setMeeterSurname(resultSet.getString(Meeter.MEETER + ".meeterSurname"));
        meeter.setPwd(resultSet.getString(Meeter.MEETER + ".pwd"));
        meeter.setBiography(resultSet.getString(Meeter.MEETER + ".biography"));
        meeter.setBirthDate(resultSet.getDate(Meeter.MEETER + ".birthDate"));
        meeter.setPublicKey(resultSet.getBytes(Meeter.MEETER + ".publicKey"));

        return meeter;
    }
}

