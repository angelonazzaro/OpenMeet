package com.openmeet.webapp.dataLayer.moderator;

import com.openmeet.shared.data.storage.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ModeratorExtractor implements ResultSetExtractor<Moderator> {

    @Override
    public Moderator extract(ResultSet resultSet) throws SQLException {

        Moderator moderator = new Moderator();

        moderator.setId(resultSet.getInt(Moderator.MODERATOR + ".id"));
        moderator.setEmail(resultSet.getString(Moderator.MODERATOR + ".email"));
        moderator.setModeratorName(resultSet.getString(Moderator.MODERATOR + ".moderatorName"));
        moderator.setModeratorSurname(resultSet.getString(Moderator.MODERATOR + ".moderatorSurname"));
        moderator.setPwd(resultSet.getString(Moderator.MODERATOR + ".pwd"));
        moderator.setProfilePic(resultSet.getString(Moderator.MODERATOR + ".profilePic"));

        return moderator;
    }
    
}
