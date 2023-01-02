package com.openmeet.shared.data.ban;

import com.openmeet.shared.data.storage.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BanExtractor implements ResultSetExtractor<Ban> {
    @Override
    public Ban extract(ResultSet resultSet) throws SQLException {

        Ban ban = new Ban();
        ban.setId(resultSet.getInt(Ban.BAN + ".id"));
        ban.setModeratorId(resultSet.getInt(Ban.BAN + ".moderatorId"));
        ban.setDescription(resultSet.getString(Ban.BAN + ".description"));
        ban.setStartTime(resultSet.getTimestamp(Ban.BAN + ".startTime"));
        ban.setEndTime(resultSet.getTimestamp(Ban.BAN + ".endTime"));
        ban.setMeeterId(resultSet.getInt(Ban.BAN + ".meeterId"));

        return ban;
    }
}
