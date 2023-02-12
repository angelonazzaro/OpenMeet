package com.openmeet.shared.data.image;

import com.openmeet.shared.data.storage.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The extractor is used to convert the ResultSet returned by the database query into an Image object.
 *
 * @see ResultSetExtractor
 *
 * @author Angelo Nazzaro
 * @author Francesco Granozio
 */
public class ImageExtractor implements ResultSetExtractor<Image> {
    /**
     * Returns an Image object from the result set.
     *
     * @param resultSet the result set deriving from the query statement.
     * @return the Image object contained in the resultSet.
     *
     * @author Francesco Granozio
     * @author Angelo Nazzaro
     */
    @Override
    public Image extract(ResultSet resultSet) throws SQLException {

        Image image = new Image();

        image.setId(resultSet.getInt(Image.IMAGE_ID));
        image.setPath(resultSet.getString(Image.IMAGE_PATH));
        image.setMeeterId(resultSet.getInt(Image.IMAGE_MEETER_ID));

        return image;
    }
}
