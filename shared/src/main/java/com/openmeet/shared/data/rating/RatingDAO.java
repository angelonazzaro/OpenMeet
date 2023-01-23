package com.openmeet.shared.data.rating;

import com.openmeet.shared.data.storage.DAO;
import com.openmeet.shared.data.storage.GenericDAO;
import com.openmeet.shared.data.storage.SQLDAO;
import com.openmeet.shared.exceptions.InvalidPrimaryKeyException;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class RatingDAO extends SQLDAO implements DAO<Rating> {

    public RatingDAO(DataSource source) {
        super(source);
    }

    @Override
    public List<Rating> doRetrieveByCondition(String condition) throws SQLException {
        return GenericDAO.genericDoRetrieveByCondition(Rating.RATING, condition, new RatingExtractor(), source);
    }

    @Override
    public Rating doRetrieveByKey(String key) throws SQLException, InvalidPrimaryKeyException {

        if (key == null) {
            throw new InvalidPrimaryKeyException(key);
        }

        List<Rating> rating = doRetrieveByCondition(
                String.format("%s = '%s'", Rating.RATING_ID, key)
        );

        return rating.isEmpty() ? null : rating.get(0);
    }

    @Override
    public List<Rating> doRetrieveAll() throws SQLException {
        return doRetrieveByCondition("TRUE");
    }

    @Override
    public List<Rating> doRetrieveAll(int row_count) throws SQLException {
        return doRetrieveByCondition("TRUE LIMIT " + row_count);
    }

    @Override
    public List<Rating> doRetrieveAll(int offset, int row_count) throws SQLException {
        return doRetrieveByCondition("TRUE LIMIT " + offset + ", " + row_count);
    }

    @Override
    public boolean doSave(Rating obj) throws SQLException {
        return GenericDAO.genericDoSave(Rating.RATING, obj.toHashMap(), this.source);
    }

    @Override
    public boolean doSave(HashMap<String, ?> values) throws SQLException {
        return GenericDAO.genericDoSave(Rating.RATING, values, this.source);
    }

    @Override
    public boolean doUpdate(HashMap<String, ?> values, String condition) throws SQLException {
        return GenericDAO.genericDoUpdate(Rating.RATING, condition, values, this.source);
    }

    @Override
    public boolean doSaveOrUpdate(Rating obj) throws SQLException {

        if (doRetrieveByKey(String.valueOf(obj.getId())) == null)
            return doSave(obj);

        return doUpdate(obj.toHashMap(),
                String.format("%s = '%s'", Rating.RATING_ID, obj.getId()));
    }

    @Override
    public boolean doDelete(String condition) throws SQLException {
        return GenericDAO.genericDoDelete(Rating.RATING, condition, this.source);
    }
}