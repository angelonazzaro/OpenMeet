package com.openmeet.webservice.proxies;

import com.google.gson.Gson;
import com.openmeet.shared.data.ban.Ban;
import com.openmeet.shared.data.meeter.Meeter;
import com.openmeet.shared.data.meeter.MeeterDAO;
import com.openmeet.shared.data.storage.DAO;
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

        logger.log(Level.INFO, "MeeterProxyDAO:doRetrieveByCondition() - condition: " + condition);

        condition = request.getParameter("condition");

        if (!ResponseHelper.checkStringFields(condition)) {
            throw new InvalidParameterException("Missing parameters - condition");
        }

        List<Meeter> meeters = dao.doRetrieveByCondition(condition);

        String json = new Gson().toJson(meeters);

        HashMap<String, String> values = new HashMap<>();
        values.put("status", "success");
        values.put("data", json);
        ResponseHelper.sendGenericResponse(out, values);

        logger.log(Level.INFO, "MeeterProxyDAO:doRetrieveByCondition() - meeters: " + meeters);

        return meeters;
    }

    @Override
    public Meeter doRetrieveByKey(String key) throws SQLException, InvalidPrimaryKeyException {

        logger.log(Level.INFO, "MeeterProxyDAO:doRetrieveByKey() - key: " + key);

        key = request.getParameter("key");

        if (!ResponseHelper.checkStringFields(key)) {
            throw new InvalidParameterException("Missing parameters - key");
        }

        Meeter meeter = dao.doRetrieveByKey(key);

        String json = new Gson().toJson(meeter);

        HashMap<String, String> values = new HashMap<>();
        values.put("status", "success");
        values.put("data", json);
        ResponseHelper.sendGenericResponse(out, values);

        logger.log(Level.INFO, "MeeterProxyDAO:doRetrieveByKey() - meeter: " + meeter);

        return meeter;
    }

    @Override
    public List<Meeter> doRetrieveAll() throws SQLException {

        logger.log(Level.INFO, "MeeterProxyDAO:doRetrieveAll()");

        List<Meeter> meeters = dao.doRetrieveByCondition("TRUE");

        String json = new Gson().toJson(meeters);

        HashMap<String, String> values = new HashMap<>();
        values.put("status", "success");
        values.put("data", json);
        ResponseHelper.sendGenericResponse(out, values);

        logger.log(Level.INFO, "MeeterProxyDAO:doRetrieveAll() - meeters: " + meeters);

        return meeters;
    }

    @Override
    public List<Meeter> doRetrieveAll(int row_count) throws SQLException, InvalidParameterException, NumberFormatException {

        logger.log(Level.INFO, "MeeterProxyDAO:doRetrieveAll() - row_count: " + row_count);

        row_count = Integer.parseInt(request.getParameter("row_count"));

        if (row_count <= 0) {
            throw new InvalidParameterException("Invalid parameters - row count");
        }

        List<Meeter> meeters = dao.doRetrieveByCondition("TRUE LIMIT " + row_count);

        String json = new Gson().toJson(meeters);

        HashMap<String, String> values = new HashMap<>();
        values.put("status", "success");
        values.put("data", json);
        ResponseHelper.sendGenericResponse(out, values);

        logger.log(Level.INFO, "MeeterProxyDAO:doRetrieveAll() - meeters: " + meeters);

        return meeters;
    }

    @Override
    public List<Meeter> doRetrieveAll(int offset, int row_count) throws SQLException, InvalidParameterException {

        logger.log(Level.INFO, "MeeterProxyDAO:doRetrieveAll() - offset: " + offset + ", row_count: " + row_count);

        row_count = Integer.parseInt(request.getParameter("row_count"));
        offset = Integer.parseInt(request.getParameter("offset"));

        if (row_count <= 0) {
            throw new InvalidParameterException("Invalid parameters - row count");
        }
        if (offset < 0) {
            throw new InvalidParameterException("Invalid parameters - offset");
        }

        List<Meeter> meeters = dao.doRetrieveByCondition("TRUE LIMIT " + offset + ", " + row_count);

        String json = new Gson().toJson(meeters);

        HashMap<String, String> values = new HashMap<>();
        values.put("status", "success");
        values.put("data", json);
        ResponseHelper.sendGenericResponse(out, values);

        logger.log(Level.INFO, "MeeterProxyDAO:doRetrieveAll() - meeters: " + meeters);

        return meeters;
    }

    @Override
    public boolean doSave(Meeter obj) throws SQLException {

        logger.log(Level.INFO, "MeeterProxyDAO:doSave() - obj: " + obj);

        obj = new Gson().fromJson(request.getParameter("meeter"), Meeter.class);

        boolean isSuccessful = dao.doSave(obj);

        HashMap<String, String> values = new HashMap<>();
        values.put("status", "success");
        values.put("data", String.valueOf(isSuccessful));
        ResponseHelper.sendGenericResponse(out, values);

        logger.log(Level.INFO, "MeeterProxyDAO:doSave() - isSuccessful: " + isSuccessful);

        return isSuccessful;
    }

    @Override
    public boolean doSave(HashMap<String, ?> values) throws SQLException {

        logger.log(Level.INFO, "MeeterProxyDAO:doSave() - values: " + values);

        values = new Gson().fromJson(request.getParameter("values"), HashMap.class);

        boolean isSuccessful = dao.doSave(values);

        HashMap<String, String> val = new HashMap<>();
        val.put("status", "success");
        val.put("data", String.valueOf(isSuccessful));

        ResponseHelper.sendGenericResponse(out, val);

        logger.log(Level.INFO, "MeeterProxyDAO:doSave() - isSuccessful: " + isSuccessful);

        return isSuccessful;
    }

    @Override
    public boolean doUpdate(HashMap<String, ?> values, String condition) throws SQLException, InvalidParameterException {

        logger.log(Level.INFO, "MeeterProxyDAO:doUpdate() - values: " + values + ", condition: " + condition);

        values = new Gson().fromJson(request.getParameter("values"), HashMap.class);
        condition = request.getParameter("condition");

        if (!ResponseHelper.checkStringFields(condition)) {
            throw new InvalidParameterException("Missing parameters - condition");
        }

        boolean isSuccessful = dao.doUpdate(values, condition);

        HashMap<String, String> val = new HashMap<>();
        val.put("status", "success");
        val.put("data", String.valueOf(isSuccessful));

        ResponseHelper.sendGenericResponse(out, val);

        logger.log(Level.INFO, "MeeterProxyDAO:doUpdate() - isSuccessful: " + isSuccessful);

        return isSuccessful;
    }

    @Override
    public boolean doSaveOrUpdate(Meeter obj) throws SQLException {

        logger.log(Level.INFO, "MeeterProxyDAO:doSaveOrUpdate() - obj: " + obj);

        obj = new Gson().fromJson(request.getParameter("meeter"), Meeter.class);

        boolean isSuccessful = dao.doSaveOrUpdate(obj);

        HashMap<String, String> values = new HashMap<>();
        values.put("status", "success");
        values.put("data", String.valueOf(isSuccessful));

        ResponseHelper.sendGenericResponse(out, values);

        logger.log(Level.INFO, "MeeterProxyDAO:doSaveOrUpdate() - isSuccessful: " + isSuccessful);

        return isSuccessful;
    }

    @Override
    public boolean doDelete(String condition) throws SQLException {

        logger.log(Level.INFO, "MeeterProxyDAO:doDelete() - condition: " + condition);

        condition = request.getParameter("condition");

        boolean isSuccessful = dao.doDelete(condition);

        HashMap<String, String> values = new HashMap<>();
        values.put("status", "success");
        values.put("data", String.valueOf(isSuccessful));

        ResponseHelper.sendGenericResponse(out, values);

        logger.log(Level.INFO, "MeeterProxyDAO:doDelete() - isSuccessful: " + isSuccessful);

        return isSuccessful;
    }
}


