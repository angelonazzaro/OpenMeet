package com.openmeet.webapp.dataLayer.moderator;

import com.openmeet.shared.data.storage.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ModeratorExtractor implements ResultSetExtractor<Moderator> {

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
