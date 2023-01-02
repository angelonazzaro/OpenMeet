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
        meeter.setName(resultSet.getString(Meeter.MEETER + ".name"));
        meeter.setSurname(resultSet.getString(Meeter.MEETER + ".surname"));
        meeter.setPassword(resultSet.getString(Meeter.MEETER + ".password"));
        meeter.setBiography(resultSet.getString(Meeter.MEETER + ".biography"));
        meeter.setBirthDate(resultSet.getDate(Meeter.MEETER + ".birthDate"));

        return meeter;
    }
}

