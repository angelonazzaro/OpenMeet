package com.openmeet.shared.data.message;

import com.openmeet.shared.data.storage.DAO;
import com.openmeet.shared.data.storage.GenericDAO;
import com.openmeet.shared.data.storage.SQLDAO;
import com.openmeet.shared.exceptions.InvalidPrimaryKeyException;

import java.sql.SQLException;
import java.util.List;
import java.util.HashMap;
import javax.sql.DataSource;

public class MessageDAO extends SQLDAO implements DAO<Message> {

    public MessageDAO(DataSource source) {
        super(source);
    }

    @Override
    public List<Message> doRetrieveByCondition(String condition) throws SQLException {
        return GenericDAO.genericDoRetrieveByCondition(Message.MESSAGE, condition, new MessageExtractor(), source);
    }

    @Override
    public List<Message> doRetrieveByCondition(String condition, int row_count) throws SQLException {
        return GenericDAO.genericDoRetrieveByCondition(Message.MESSAGE, condition + " LIMIT " + row_count,
                new MessageExtractor(), source);
    }

    @Override
    public List<Message> doRetrieveByCondition(String condition, int offset, int row_count) throws SQLException {
        return GenericDAO.genericDoRetrieveByCondition(Message.MESSAGE, condition + " LIMIT " + offset + ", " + row_count,
                new MessageExtractor(), source);
    }

    @Override
    public Message doRetrieveByKey(String key) throws SQLException, InvalidPrimaryKeyException {

        if (key == null) {
            throw new InvalidPrimaryKeyException(key);
        }
        List<Message> message = doRetrieveByCondition(
                String.format("%s = '%s'", Message.MESSAGE_ID, key)
        );
        return message.isEmpty() ? null : message.get(0);
    }

    @Override
    public List<Message> doRetrieveAll() throws SQLException {
        return doRetrieveByCondition("TRUE");
    }

    @Override
    public List<Message> doRetrieveAll(int row_count) throws SQLException {
        return doRetrieveByCondition("TRUE LIMIT " + row_count);
    }

    @Override
    public List<Message> doRetrieveAll(int offset, int row_count) throws SQLException {
        return doRetrieveByCondition("TRUE LIMIT " + offset + ", " + row_count);
    }

    @Override
    public boolean doSave(Message obj) throws SQLException {
        return GenericDAO.genericDoSave(Message.MESSAGE, obj.toHashMap(), this.source);
    }

    @Override
    public boolean doSave(HashMap<String, ?> values) throws SQLException {
        return GenericDAO.genericDoSave(Message.MESSAGE, values, this.source);
    }

    @Override
    public boolean doUpdate(HashMap<String, ?> values, String condition) throws SQLException {
        return GenericDAO.genericDoUpdate(Message.MESSAGE, condition, values, this.source);
    }

    @Override
    public boolean doSaveOrUpdate(Message obj) throws SQLException {

        if (doRetrieveByKey(String.valueOf(obj.getId())) == null)
            return doSave(obj);

        return doUpdate(obj.toHashMap(), String.format("%s = '%s'", Message.MESSAGE_ID, obj.getId()));
    }

    @Override
    public boolean doDelete(String condition) throws SQLException {
        return GenericDAO.genericDoDelete(Message.MESSAGE, condition, source);
    }
}