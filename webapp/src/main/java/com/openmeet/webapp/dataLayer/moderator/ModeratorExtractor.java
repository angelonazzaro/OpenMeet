package com.openmeet.webapp.dataLayer.moderator;

import com.openmeet.shared.data.storage.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The extractor is used to convert the ResultSet returned by the database query into a Moderator object.
 *
 * @author Angelo Nazzaro
 * @see ResultSetExtractor
 */
public class ModeratorExtractor implements ResultSetExtractor<Moderator> {

    /**
     * Returns a Moderator object from the result set.
     *
     * @param resultSet the result set deriving from the query statement.
     * @return the Moderator object contained in the resultSet.
     * @author Angelo Nazzaro
     */
    @Override
    public Moderator extract(ResultSet resultSet) throws SQLException {

        Moderator moderator = new Moderator();

        moderator.setId(resultSet.getInt(Moderator.MODERATOR_ID));
        moderator.setEmail(resultSet.getString(Moderator.MODERATOR_EMAIL));
        moderator.setModeratorName(resultSet.getString(Moderator.MODERATOR_MODERATOR_NAME));
        moderator.setModeratorSurname(resultSet.getString(Moderator.MODERATOR_MODERATOR_SURNAME));
        moderator.setPwd(resultSet.getString(Moderator.MODERATOR_PWD));
        moderator.setProfilePic(resultSet.getString(Moderator.MODERATOR_PROFILE_PIC));

        return moderator;
    }

}
