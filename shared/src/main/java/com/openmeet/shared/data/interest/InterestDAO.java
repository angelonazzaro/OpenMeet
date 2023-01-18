package com.openmeet.shared.data.interest;

import com.openmeet.shared.data.storage.DAO;
import com.openmeet.shared.data.storage.SQLDAO;
import com.openmeet.shared.exceptions.InvalidPrimaryKeyException;

import java.sql.SQLException;
import java.util.List;
import java.util.HashMap;
import javax.sql.DataSource;

public class InterestDAO extends SQLDAO implements DAO<Interest> {

    public InterestDAO(DataSource source) {
        super(source);
    }

    @Override
    public List<Interest> doRetrieveByCondition(String condition) throws SQLException {
        return genericDoRetrieveByCondition(Interest.INTEREST, condition, new InterestExtractor(), source);
    }

    @Override
    public Interest doRetrieveByKey(String key) throws SQLException, InvalidPrimaryKeyException {

        if (key == null) {
            throw new InvalidPrimaryKeyException(key);
        }
        List<Interest> interest = doRetrieveByCondition(String.format("%s = '%s'", Interest.INTEREST_ID, key));
        return interest.isEmpty() ? null : interest.get(0);
    }

    @Override
    public List<Interest> doRetrieveAll() throws SQLException {
        return doRetrieveByCondition("TRUE");
    }

    @Override
    public List<Interest> doRetrieveAll(int row_count) throws SQLException {
        return doRetrieveByCondition("TRUE LIMIT " + row_count);
    }

    @Override
    public List<Interest> doRetrieveAll(int offset, int row_count) throws SQLException {
        return doRetrieveByCondition("TRUE LIMIT " + offset + ", " + row_count);
    }

    @Override
    public boolean doSave(Interest obj) throws SQLException {
        return genericDoSave(Interest.INTEREST, obj.toHashMap(), this.source);
    }

    @Override
    public boolean doSave(HashMap<String, ?> values) throws SQLException {
        return genericDoSave(Interest.INTEREST, values, this.source);
    }

    @Override
    public boolean doUpdate(HashMap<String, ?> values, String condition) throws SQLException {
        return genericDoUpdate(Interest.INTEREST, condition, values, this.source);
    }

    @Override
    public boolean doSaveOrUpdate(Interest obj) throws SQLException {

        if (doRetrieveByKey(String.valueOf(obj.getId())) == null)
            return doSave(obj);

        return doUpdate(obj.toHashMap(),
                String.format("%s = '%s'", Interest.INTEREST_ID, obj.getId()));
    }

    @Override
    public boolean doDelete(String condition) throws SQLException {
        return genericDoDelete(Interest.INTEREST_ID, condition, this.source);
    }
}