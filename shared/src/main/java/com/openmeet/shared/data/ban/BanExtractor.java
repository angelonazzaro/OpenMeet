package com.openmeet.shared.data.ban;

import com.openmeet.shared.data.storage.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * The extractor is used to convert the ResultSet returned by the database query into a Ban object.
 *
 * @author Angelo Nazzaro
 * @author Francesco Granozio
 */
public class BanExtractor implements ResultSetExtractor<Ban> {

    /**
     * Returns a Ban object from the result set.
     *
     * @param resultSet the result set deriving from the query statement.
     * @return the Ban object contained in the resultSet.
     *
     * @author Francesco Granozio
     * @author Angelo Nazzaro
     */
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
