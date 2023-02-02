package com.openmeet.shared.data.image;


import com.openmeet.shared.data.storage.DAO;
import com.openmeet.shared.data.storage.GenericDAO;
import com.openmeet.shared.data.storage.SQLDAO;
import com.openmeet.shared.exceptions.InvalidPrimaryKeyException;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class ImageDAO extends SQLDAO implements DAO<Image> {

    public ImageDAO(DataSource source) {
        super(source);
    }

    @Override
    public List<Image> doRetrieveByCondition(String condition) throws SQLException {
        return GenericDAO.genericDoRetrieveByCondition(Image.IMAGE, condition, new ImageExtractor(), source);
    }

    @Override
    public List<Image> doRetrieveByCondition(String condition, int offset, int rows_count) throws SQLException {
        return GenericDAO.genericDoRetrieveByCondition(Image.IMAGE, condition + " LIMIT " + offset + ", " + rows_count,
                new ImageExtractor(), source);
    }

    @Override
    public Image doRetrieveByKey(String key) throws SQLException, InvalidPrimaryKeyException {

        if (key == null) {
            throw new InvalidPrimaryKeyException(key);
        }
        List<Image> image = doRetrieveByCondition(
                String.format("%s = '%s'", Image.IMAGE_ID, key)
        );
        return image.isEmpty() ? null : image.get(0);
    }

    @Override
    public List<Image> doRetrieveAll() throws SQLException {
        return doRetrieveByCondition("TRUE");
    }

    @Override
    public List<Image> doRetrieveAll(int row_count) throws SQLException {
        return doRetrieveByCondition("TRUE LIMIT " + row_count);
    }

    @Override
    public List<Image> doRetrieveAll(int offset, int row_count) throws SQLException {
        return doRetrieveByCondition("TRUE LIMIT " + offset + ", " + row_count);
    }

    @Override
    public boolean doSave(Image obj) throws SQLException {
        return GenericDAO.genericDoSave(Image.IMAGE, obj.toHashMap(), this.source);
    }

    @Override
    public boolean doSave(HashMap<String, ?> values) throws SQLException {
        return GenericDAO.genericDoSave(Image.IMAGE, values, this.source);
    }

    @Override
    public boolean doUpdate(HashMap<String, ?> values, String condition) throws SQLException {
        return GenericDAO.genericDoUpdate(Image.IMAGE, condition, values, this.source);
    }

    @Override
    public boolean doSaveOrUpdate(Image obj) throws SQLException {

        if (doRetrieveByKey(String.valueOf(obj.getId())) == null)
            return doSave(obj);

        return doUpdate(obj.toHashMap(),
                String.format("%s = '%s'", Image.IMAGE_ID, obj.getId()));
    }

    @Override
    public boolean doDelete(String condition) throws SQLException {
        return GenericDAO.genericDoDelete(Image.IMAGE, condition, this.source);
    }
}
