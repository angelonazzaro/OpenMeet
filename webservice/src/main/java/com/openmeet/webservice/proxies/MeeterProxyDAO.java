package com.openmeet.webservice.proxies;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.openmeet.shared.data.ban.Ban;
import com.openmeet.shared.data.meeter.Meeter;
import com.openmeet.shared.data.meeter.MeeterDAO;
import com.openmeet.shared.data.storage.DAO;
import com.openmeet.shared.data.storage.GenericProxyDAO;
import com.openmeet.shared.exceptions.InvalidPrimaryKeyException;
import com.openmeet.shared.helpers.ResponseHelper;
import com.openmeet.webservice.InvalidParameterException;
import jakarta.servlet.http.HttpServletRequest;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;


public class MeeterProxyDAO extends ProxyDAO<Meeter> implements DAO<Meeter> {

    public MeeterProxyDAO(MeeterDAO dao, HttpServletRequest request, PrintWriter out) {
        super(dao, request, out);
    }

    @Override
    public List<Meeter> doRetrieveByCondition(String condition) throws SQLException, InvalidParameterException {

        condition = request.getParameter("condition");

        if (!ResponseHelper.checkStringFields(condition)) {
            throw new java.security.InvalidParameterException("Missing parameters - condition");
        }

        logger.log(Level.INFO, "MeeterProxyDAO:doRetrieveByCondition() - condition: " + condition);

        List<Meeter> meeters = GenericProxyDAO.genericProxyDoRetrieveByCondition(condition, dao, out);

        logger.log(Level.INFO, "MeeterProxyDAO:doRetrieveByCondition() - meeters: " + meeters);

        return meeters;
    }

    @Override
    public Meeter doRetrieveByKey(String key) throws SQLException, InvalidPrimaryKeyException {

        key = request.getParameter("key");

        if (!ResponseHelper.checkStringFields(key)) {
            throw new java.security.InvalidParameterException("Missing parameters - key");
        }

        logger.log(Level.INFO, "MeeterProxyDAO:doRetrieveByKey() - key: " + key);

        Meeter meeter = GenericProxyDAO.genericProxyDoRetrieveByKey(key, dao, out);

        logger.log(Level.INFO, "MeeterProxyDAO:doRetrieveByKey() - meeter: " + meeter);

        return meeter;
    }

    @Override
    public List<Meeter> doRetrieveAll() throws SQLException {

        logger.log(Level.INFO, "MeeterProxyDAO:doRetrieveAll()");

        List<Meeter> meeters = GenericProxyDAO.genericProxyDoRetrieveByCondition("TRUE", dao, out);

        logger.log(Level.INFO, "MeeterProxyDAO:doRetrieveAll() - meeters: " + meeters);

        return meeters;
    }

    @Override
    public List<Meeter> doRetrieveAll(int row_count) throws SQLException, InvalidParameterException, NumberFormatException {

        row_count = Integer.parseInt(request.getParameter("row_count"));

        logger.log(Level.INFO, "MeeterProxyDAO:doRetrieveAll() - row_count: " + row_count);

        if (row_count <= 0) {
            throw new InvalidParameterException("Invalid parameters - row count");
        }

        List<Meeter> meeters = GenericProxyDAO.genericProxyDoRetrieveByCondition("TRUE LIMIT " + row_count, dao, out);

        logger.log(Level.INFO, "MeeterProxyDAO:doRetrieveAll() - meeters: " + meeters);

        return meeters;
    }

    @Override
    public List<Meeter> doRetrieveAll(int offset, int row_count) throws SQLException, InvalidParameterException {

        row_count = Integer.parseInt(request.getParameter("row_count"));
        offset = Integer.parseInt(request.getParameter("offset"));

        logger.log(Level.INFO, "MeeterProxyDAO:doRetrieveAll() - offset: " + offset + ", row_count: " + row_count);

        if (row_count <= 0) {
            throw new InvalidParameterException("Invalid parameters - row count");
        }
        if (offset < 0) {
            throw new InvalidParameterException("Invalid parameters - offset");
        }

        List<Meeter> meeters = GenericProxyDAO.genericProxyDoRetrieveByCondition("TRUE LIMIT " + offset + ", " + row_count, dao, out);

        logger.log(Level.INFO, "MeeterProxyDAO:doRetrieveAll() - meeters: " + meeters);

        return meeters;
    }

    @Override
    public boolean doSave(Meeter obj) throws SQLException {

        Gson builder = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        obj = builder.fromJson(request.getParameter("meeter"), Meeter.class);

        logger.log(Level.INFO, "MeeterProxyDAO:doSave() - obj: " + obj);

        boolean isSuccessful = GenericProxyDAO.genericProxyDoSave(obj, dao, out);

        logger.log(Level.INFO, "MeeterProxyDAO:doSave() - isSuccessful: " + isSuccessful);

        return isSuccessful;
    }

    @Override
    public boolean doSave(HashMap<String, ?> values) throws SQLException {

        Gson builder = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        values = builder.fromJson(request.getParameter("values"), HashMap.class);

        logger.log(Level.INFO, "MeeterProxyDAO:doSave() - values: " + values);

        boolean isSuccessful = GenericProxyDAO.genericProxyDoSave(values, dao, out);

        logger.log(Level.INFO, "MeeterProxyDAO:doSave() - isSuccessful: " + isSuccessful);

        return isSuccessful;
    }

    @Override
    public boolean doUpdate(HashMap<String, ?> values, String condition) throws SQLException, InvalidParameterException {

        Gson builder = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        values = builder.fromJson(request.getParameter("values"), HashMap.class);
        condition = request.getParameter("condition");

        logger.log(Level.INFO, "MeeterProxyDAO:doUpdate() - values: " + values + ", condition: " + condition);

        if (!ResponseHelper.checkStringFields(condition)) {
            throw new InvalidParameterException("Missing parameters - condition");
        }

        boolean isSuccessful = GenericProxyDAO.genericProxyDoUpdate(values, condition, dao, out);

        logger.log(Level.INFO, "MeeterProxyDAO:doUpdate() - isSuccessful: " + isSuccessful);

        return isSuccessful;
    }

    @Override
    public boolean doSaveOrUpdate(Meeter obj) throws SQLException {

        Gson builder = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        obj = builder.fromJson(request.getParameter("meeter"), Meeter.class);

        logger.log(Level.INFO, "MeeterProxyDAO:doSaveOrUpdate() - obj: " + obj);

        boolean isSuccessful = GenericProxyDAO.genericProxyDoSaveOrUpdate(obj, dao, out);

        logger.log(Level.INFO, "MeeterProxyDAO:doSaveOrUpdate() - isSuccessful: " + isSuccessful);

        return isSuccessful;
    }

    @Override
    public boolean doDelete(String condition) throws SQLException {

        condition = request.getParameter("condition");

        logger.log(Level.INFO, "MeeterProxyDAO:doDelete() - condition: " + condition);

        boolean isSuccessful = GenericProxyDAO.genericProxyDoDelete(condition, dao, out);

        logger.log(Level.INFO, "MeeterProxyDAO:doDelete() - isSuccessful: " + isSuccessful);

        return isSuccessful;
    }
}


