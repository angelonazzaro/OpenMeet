package com.openmeet.shared.data.ban;

import com.openmeet.shared.data.storage.DAO;
import com.openmeet.shared.data.storage.GenericDAO;
import com.openmeet.shared.data.storage.SQLDAO;
import com.openmeet.shared.exceptions.InvalidPrimaryKeyException;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class BanDAO extends SQLDAO implements DAO<Ban> {

    public BanDAO(DataSource source) {
        super(source);
    }

    @Override
    public List<Ban> doRetrieveByCondition(String condition) throws SQLException {
        return GenericDAO.genericDoRetrieveByCondition(Ban.BAN, condition, new BanExtractor(), source);
    }

    @Override
    public List<Ban> doRetrieveByCondition(String condition, int offset, int row_count) throws SQLException {
        return GenericDAO.genericDoRetrieveByCondition(Ban.BAN, condition + " LIMIT " + offset + ", " + row_count,
                new BanExtractor(), source);
    }

    @Override
    public Ban doRetrieveByKey(String key) throws SQLException, InvalidPrimaryKeyException {

        if (key == null) {
            throw new InvalidPrimaryKeyException(key);
        }
        List<Ban> ban = doRetrieveByCondition(
                String.format("%s = '%s'", Ban.BAN_ID, key)
        );
        return ban.isEmpty() ? null : ban.get(0);
    }

    @Override
    public List<Ban> doRetrieveAll() throws SQLException {
        return doRetrieveByCondition("TRUE");
    }

    @Override
    public List<Ban> doRetrieveAll(int row_count) throws SQLException {
        return doRetrieveByCondition("TRUE LIMIT " + row_count);
    }

    @Override
    public List<Ban> doRetrieveAll(int offset, int row_count) throws SQLException {
        return doRetrieveByCondition("TRUE LIMIT " + offset + ", " + row_count);
    }

    @Override
    public boolean doSave(Ban obj) throws SQLException {
        return GenericDAO.genericDoSave(Ban.BAN, obj.toHashMap(), this.source);
    }

    @Override
    public boolean doSave(HashMap<String, ?> values) throws SQLException {
        return GenericDAO.genericDoSave(Ban.BAN, values, this.source);
    }

    @Override
    public boolean doUpdate(HashMap<String, ?> values, String condition) throws SQLException {
        return GenericDAO.genericDoUpdate(Ban.BAN, condition, values, this.source);
    }

    @Override
    public boolean doSaveOrUpdate(Ban obj) throws SQLException {

        if (doRetrieveByKey(String.valueOf(obj.getId())) == null)
            return doSave(obj);

        return doUpdate(obj.toHashMap(),
                String.format("%s = '%s'", Ban.BAN_ID, obj.getId()));
    }

    @Override
    public boolean doDelete(String condition) throws SQLException {
        return GenericDAO.genericDoDelete(Ban.BAN, condition, this.source);
    }
}
