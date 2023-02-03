package com.openmeet.shared.data.meeter_interest;

import com.openmeet.shared.data.storage.DAO;
import com.openmeet.shared.data.storage.GenericDAO;
import com.openmeet.shared.data.storage.SQLDAO;
import com.openmeet.shared.exceptions.InvalidPrimaryKeyException;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class Meeter_InterestDAO extends SQLDAO implements DAO<Meeter_Interest> {

    public Meeter_InterestDAO(DataSource source) {
        super(source);
    }

    @Override
    public List<Meeter_Interest> doRetrieveByCondition(String condition) throws SQLException {
        return GenericDAO.genericDoRetrieveByCondition(Meeter_Interest.MEETER_INTEREST, condition, new Meeter_InterestExtractor(), source);
    }

    @Override
    public List<Meeter_Interest> doRetrieveByCondition(String condition, int offset, int row_count) throws SQLException {
        return GenericDAO.genericDoRetrieveByCondition(Meeter_Interest.MEETER_INTEREST, condition + " LIMIT " + offset + ", " + row_count, new Meeter_InterestExtractor(), source);
    }

    @Override
    public Meeter_Interest doRetrieveByKey(String key) throws SQLException, InvalidPrimaryKeyException {

        if (key == null) {
            throw new InvalidPrimaryKeyException(key);
        }
        List<Meeter_Interest> meeterInterest = doRetrieveByCondition(
                String.format("%s = '%s'", Meeter_Interest.MEETER_INTEREST_MEETER_ID, key)
        );
        return meeterInterest.isEmpty() ? null : meeterInterest.get(0);
    }

    @Override
    public List<Meeter_Interest> doRetrieveAll() throws SQLException {
        return doRetrieveByCondition("TRUE");
    }

    @Override
    public List<Meeter_Interest> doRetrieveAll(int row_count) throws SQLException {
        return doRetrieveByCondition("TRUE LIMIT " + row_count);
    }

    @Override
    public List<Meeter_Interest> doRetrieveAll(int offset, int row_count) throws SQLException {
        return doRetrieveByCondition("TRUE LIMIT " + offset + ", " + row_count);
    }

    @Override
    public boolean doSave(Meeter_Interest obj) throws SQLException {
        return GenericDAO.genericDoSave(Meeter_Interest.MEETER_INTEREST, obj.toHashMap(), this.source);
    }

    @Override
    public boolean doSave(HashMap<String, ?> values) throws SQLException {
        return GenericDAO.genericDoSave(Meeter_Interest.MEETER_INTEREST, values, this.source);
    }

    @Override
    public boolean doUpdate(HashMap<String, ?> values, String condition) throws SQLException {
        return GenericDAO.genericDoUpdate(Meeter_Interest.MEETER_INTEREST, condition, values, this.source);
    }

    @Override
    public boolean doSaveOrUpdate(Meeter_Interest obj) throws SQLException {

        if (doRetrieveByKey(String.valueOf(obj.getId())) == null)
            return doSave(obj);

        return doUpdate(obj.toHashMap(),
                String.format("%s = '%s'", Meeter_Interest.MEETER_INTEREST_MEETER_ID, obj.getId()));
    }

    @Override
    public boolean doDelete(String condition) throws SQLException {
        return GenericDAO.genericDoDelete(Meeter_Interest.MEETER_INTEREST, condition, this.source);
    }
}