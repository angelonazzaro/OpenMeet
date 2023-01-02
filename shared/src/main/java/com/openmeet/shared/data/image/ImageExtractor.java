package com.openmeet.shared.data.image;

import com.openmeet.shared.data.storage.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ImageExtractor implements ResultSetExtractor<Image> {
    @Override
    public Image extract(ResultSet resultSet) throws SQLException {

        Image image = new Image();

        image.setId(resultSet.getInt(Image.IMAGE + ".id"));
        image.setPath(resultSet.getString(Image.IMAGE + ".path"));
        image.setMeeterId(resultSet.getInt(Image.IMAGE + ".meeterId"));

        return image;
    }
}
