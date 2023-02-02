package com.openmeet.shared.data.meeter;

import com.openmeet.shared.data.storage.DAO;
import com.openmeet.shared.data.storage.GenericDAO;
import com.openmeet.shared.data.storage.SQLDAO;
import com.openmeet.shared.exceptions.InvalidPrimaryKeyException;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class MeeterDAO extends SQLDAO implements DAO<Meeter> {

    public MeeterDAO(DataSource source) {
        super(source);
    }

    @Override
    public List<Meeter> doRetrieveByCondition(String condition) throws SQLException {
        return GenericDAO.genericDoRetrieveByCondition(Meeter.MEETER, condition, new MeeterExtractor(), source);
    }

    @Override
    public List<Meeter> doRetrieveByCondition(String condition, int offset, int rows_count) throws SQLException {
        return GenericDAO.genericDoRetrieveByCondition(Meeter.MEETER, condition + " LIMIT " + offset + ", " + rows_count,
                new MeeterExtractor(), source);
    }

    @Override
    public Meeter doRetrieveByKey(String key) throws SQLException, InvalidPrimaryKeyException {

        if (key == null) {
            throw new InvalidPrimaryKeyException(key);
        }
        List<Meeter> meeter = doRetrieveByCondition(
                String.format("%s = '%s'", Meeter.MEETER_ID, key)
        );
        return meeter.isEmpty() ? null : meeter.get(0);
    }

    @Override
    public List<Meeter> doRetrieveAll() throws SQLException {
        return doRetrieveByCondition("TRUE");
    }

    @Override
    public List<Meeter> doRetrieveAll(int row_count) throws SQLException {
        return doRetrieveByCondition("TRUE LIMIT " + row_count);
    }

    @Override
    public List<Meeter> doRetrieveAll(int offset, int row_count) throws SQLException {
        return doRetrieveByCondition("TRUE LIMIT " + offset + ", " + row_count);
    }

    @Override
    public boolean doSave(Meeter obj) throws SQLException {
        return GenericDAO.genericDoSave(Meeter.MEETER, obj.toHashMap(), this.source);
    }

    @Override
    public boolean doSave(HashMap<String, ?> values) throws SQLException {
        return GenericDAO.genericDoSave(Meeter.MEETER, values, this.source);
    }


    @Override
    public boolean doUpdate(HashMap<String, ?> values, String condition) throws SQLException {
        return GenericDAO.genericDoUpdate(Meeter.MEETER, condition, values, this.source);
    }

    @Override
    public boolean doSaveOrUpdate(Meeter obj) throws SQLException {

        if (doRetrieveByKey(String.valueOf(obj.getId())) == null)
            return doSave(obj);

        return doUpdate(obj.toHashMap(),
                String.format("%s = '%s'", Meeter.MEETER_ID, obj.getId()));
    }

    @Override
    public boolean doDelete(String condition) throws SQLException {
        return GenericDAO.genericDoDelete(Meeter.MEETER, condition, this.source);
    }
}
