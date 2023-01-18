package com.openmeet.shared.data.image;

import com.openmeet.shared.data.storage.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ImageExtractor implements ResultSetExtractor<Image> {
    @Override
    public Image extract(ResultSet resultSet) throws SQLException {

        Image image = new Image();

        image.setId(resultSet.getInt(Image.IMAGE_ID));
        image.setPath(resultSet.getString(Image.IMAGE_PATH));
        image.setMeeterId(resultSet.getInt(Image.IMAGE_MEETER_ID));

        return image;
    }
}
