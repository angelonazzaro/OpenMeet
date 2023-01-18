package com.openmeet.webapp.dataLayer.moderator;

import com.openmeet.shared.data.storage.DAO;
import com.openmeet.shared.data.storage.SQLDAO;
import com.openmeet.shared.exceptions.InvalidPrimaryKeyException;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class ModeratorDAO extends SQLDAO implements DAO<Moderator> {

    public ModeratorDAO(DataSource source) {
        super(source);
    }

    @Override
    public List<Moderator> doRetrieveByCondition(String condition) throws SQLException {
        return genericDoRetrieveByCondition(Moderator.MODERATOR, condition, new ModeratorExtractor(), source);
    }

    @Override
    public Moderator doRetrieveByKey(String key) throws SQLException, InvalidPrimaryKeyException {

        if (key == null) {
            throw new InvalidPrimaryKeyException(key);
        }
        List<Moderator> moderator = doRetrieveByCondition(
                String.format("%s = '%s'", Moderator.MODERATOR_ID, key)
        );
        return moderator.isEmpty() ? null : moderator.get(0);
    }

    @Override
    public List<Moderator> doRetrieveAll() throws SQLException {
        return doRetrieveByCondition("TRUE");
    }

    @Override
    public List<Moderator> doRetrieveAll(int row_count) throws SQLException {
        return doRetrieveByCondition("TRUE LIMIT " + row_count);
    }

    @Override
    public List<Moderator> doRetrieveAll(int offset, int row_count) throws SQLException {
        return doRetrieveByCondition("TRUE LIMIT " + offset + ", " + row_count);
    }

    @Override
    public boolean doSave(Moderator obj) throws SQLException {
        return genericDoSave(Moderator.MODERATOR, obj.toHashMap(), this.source);
    }

    @Override
    public boolean doSave(HashMap<String, ?> values) throws SQLException {
        return genericDoSave(Moderator.MODERATOR, values, this.source);
    }

    @Override
    public boolean doUpdate(HashMap<String, ?> values, String condition) throws SQLException {
        return genericDoUpdate(Moderator.MODERATOR, condition, values, this.source);
    }

    @Override
    public boolean doSaveOrUpdate(Moderator obj) throws SQLException {

        if (doRetrieveByKey(String.valueOf(obj.getId())) == null)
            return doSave(obj);

        return doUpdate(obj.toHashMap(),
                String.format("%s = '%s'", Moderator.MODERATOR_ID, obj.getId()));
    }

    @Override
    public boolean doDelete(String condition) throws SQLException {
        return genericDoDelete(Moderator.MODERATOR, condition, this.source);
    }
}
