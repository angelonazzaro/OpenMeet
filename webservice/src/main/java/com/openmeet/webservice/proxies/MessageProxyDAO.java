package com.openmeet.webservice.proxies;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.openmeet.shared.data.message.Message;
import com.openmeet.shared.data.storage.DAO;
import com.openmeet.shared.data.storage.GenericProxyDAO;
import com.openmeet.shared.exceptions.InvalidPrimaryKeyException;
import com.openmeet.shared.helpers.ResponseHelper;
import com.openmeet.webservice.exceptions.InvalidParameterException;
import jakarta.servlet.http.HttpServletRequest;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

public class MessageProxyDAO extends ProxyDAO<Message> implements DAO<Message> {

    public MessageProxyDAO(DAO<Message> dao, HttpServletRequest request, PrintWriter out) {
        super(dao, request, out);
    }

    @Override
    public List<Message> doRetrieveByCondition(String condition) throws SQLException {

        condition = request.getParameter("condition");

        if (!ResponseHelper.checkStringFields(condition)) {
            throw new InvalidParameterException("Missing parameters - condition");
        }

        logger.log(Level.INFO, "MessageProxyDAO:doRetrieveByCondition() - condition: " + condition);

        List<Message> messages = GenericProxyDAO.genericProxyDoRetrieveByCondition(condition, dao, out);

        logger.log(Level.INFO, "MessageProxyDAO:doRetrieveByCondition() - messages: " + messages);

        return messages;

    }

    @Override
    public List<Message> doRetrieveByCondition(String condition, int offset, int rows_count) throws SQLException {

        condition = request.getParameter("condition");
        offset = Integer.parseInt(request.getParameter("offset"));
        rows_count = Integer.parseInt(request.getParameter("rows_count"));

        if (!ResponseHelper.checkStringFields(condition)) {
            throw new InvalidParameterException("Missing parameters - condition");
        }

        if (offset < 0) {
            throw new InvalidParameterException("Offset parameter cannot contain a negative value");
        }

        if (rows_count <= 0) {
            throw new InvalidParameterException("Rows_count parameter must be greater than 0");
        }

        logger.log(Level.INFO, "MessageProxyDAO:doRetrieveByCondition() - condition: " + condition + " LIMIT " + offset + ", " + rows_count);

        List<Message> messages = GenericProxyDAO.genericProxyDoRetrieveByCondition(condition, offset, rows_count, dao, out);

        logger.log(Level.INFO, "MessageProxyDAO:doRetrieveByCondition() - messages: " + messages);

        return messages;

    }

    @Override
    public Message doRetrieveByKey(String key) throws SQLException, InvalidPrimaryKeyException {

        key = request.getParameter("key");

        if (!ResponseHelper.checkStringFields(key)) {
            throw new InvalidParameterException("Missing parameters - key");
        }

        logger.log(Level.INFO, "MessageProxyDAO:doRetrieveByKey() - key: " + key);

        Message message = GenericProxyDAO.genericProxyDoRetrieveByKey(key, dao, out);

        logger.log(Level.INFO, "MessageProxyDAO:doRetrieveByKey() - message: " + message);

        return message;

    }

    @Override
    public List<Message> doRetrieveAll() throws SQLException {

        logger.log(Level.INFO, "MessageProxyDAO:doRetrieveAll()");

        List<Message> messages = GenericProxyDAO.genericProxyDoRetrieveByCondition("TRUE", dao, out);

        logger.log(Level.INFO, "MessageProxyDAO:doRetrieveAll() - messages: " + messages);

        return messages;

    }

    @Override
    public List<Message> doRetrieveAll(int row_count) throws SQLException {

        row_count = Integer.parseInt(request.getParameter("row_count"));

        logger.log(Level.INFO, "MessageProxyDAO:doRetrieveAll() - row_count: " + row_count);

        if (row_count <= 0) {
            throw new InvalidParameterException("Invalid parameters - row count");
        }

        List<Message> messages = GenericProxyDAO.genericProxyDoRetrieveByCondition("TRUE LIMIT " + row_count, dao, out);

        logger.log(Level.INFO, "MessageProxyDAO:doRetrieveAll() - messages: " + messages);

        return messages;

    }

    @Override
    public List<Message> doRetrieveAll(int offset, int row_count) throws SQLException {

        row_count = Integer.parseInt(request.getParameter("row_count"));
        offset = Integer.parseInt(request.getParameter("offset"));

        logger.log(Level.INFO, "MessageProxyDAO:doRetrieveAll() - offset: " + offset + ", row_count: " + row_count);

        if (row_count <= 0) {
            throw new InvalidParameterException("Invalid parameters - row count");
        }
        if (offset < 0) {
            throw new InvalidParameterException("Invalid parameters - offset");
        }

        List<Message> messages = GenericProxyDAO.genericProxyDoRetrieveByCondition("TRUE LIMIT " + offset + ", " + row_count, dao, out);

        logger.log(Level.INFO, "MessageProxyDAO:doRetrieveAll() - messages: " + messages);

        return messages;

    }

    @Override
    public boolean doSave(Message obj) throws SQLException {

        Gson builder = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        obj = builder.fromJson(request.getParameter("message"), Message.class);

        logger.log(Level.INFO, "MessageProxyDAO:doSave() - obj: " + obj);

        boolean isSuccessful = GenericProxyDAO.genericProxyDoSave(obj, dao, out);

        logger.log(Level.INFO, "MessageProxyDAO:doSave() - isSuccessful: " + isSuccessful);

        return isSuccessful;

    }

    @Override
    public boolean doSave(HashMap<String, ?> values) throws SQLException {

        Gson builder = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        values = builder.fromJson(request.getParameter("values"), HashMap.class);

        logger.log(Level.INFO, "MessageProxyDAO:doSave() - values: " + values);

        boolean isSuccessful = GenericProxyDAO.genericProxyDoSave(values, dao, out);

        logger.log(Level.INFO, "MessageProxyDAO:doSave() - isSuccessful: " + isSuccessful);

        return isSuccessful;

    }

    @Override
    public boolean doUpdate(HashMap<String, ?> values, String condition) throws SQLException {

        Gson builder = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        values = builder.fromJson(request.getParameter("values"), HashMap.class);
        condition = request.getParameter("condition");

        logger.log(Level.INFO, "MessageProxyDAO:doUpdate() - values: " + values + ", condition: " + condition);

        if (!ResponseHelper.checkStringFields(condition)) {
            throw new InvalidParameterException("Missing parameters - condition");
        }

        boolean isSuccessful = GenericProxyDAO.genericProxyDoUpdate(values, condition, dao, out);

        logger.log(Level.INFO, "MessageProxyDAO:doUpdate() - isSuccessful: " + isSuccessful);

        return isSuccessful;

    }

    @Override
    public boolean doSaveOrUpdate(Message obj) throws SQLException {

        Gson builder = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        obj = builder.fromJson(request.getParameter("message"), Message.class);

        logger.log(Level.INFO, "MessageProxyDAO:doSaveOrUpdate() - obj: " + obj);

        boolean isSuccessful = GenericProxyDAO.genericProxyDoSaveOrUpdate(obj, dao, out);

        logger.log(Level.INFO, "MessageProxyDAO:doSaveOrUpdate() - isSuccessful: " + isSuccessful);

        return isSuccessful;

    }

    @Override
    public boolean doDelete(String condition) throws SQLException {

        condition = request.getParameter("condition");

        logger.log(Level.INFO, "MessageProxyDAO:doDelete() - condition: " + condition);

        boolean isSuccessful = GenericProxyDAO.genericProxyDoDelete(condition, dao, out);

        logger.log(Level.INFO, "MessageProxyDAO:doDelete() - isSuccessful: " + isSuccessful);

        return isSuccessful;

    }
}
