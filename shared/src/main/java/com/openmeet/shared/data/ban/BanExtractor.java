package com.openmeet.shared.data.ban;

import com.openmeet.shared.data.storage.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BanExtractor implements ResultSetExtractor<Ban> {
    @Override
    public Ban extract(ResultSet resultSet) throws SQLException {

        Ban ban = new Ban();

        ban.setId(resultSet.getInt(Ban.BAN_ID));
        ban.setModeratorId(resultSet.getInt(Ban.BAN_MODERATOR_ID));
        ban.setDescription(resultSet.getString(Ban.BAN_DESCRIPTION));
        ban.setStartTime(resultSet.getTimestamp(Ban.BAN_START_TIME));
        ban.setEndTime(resultSet.getTimestamp(Ban.BAN_END_TIME));
        ban.setMeeterId(resultSet.getInt(Ban.BAN_MEETER_ID));

        return ban;
    }
}
