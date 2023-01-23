package com.openmeet.webservice.proxies;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.openmeet.shared.data.meeter.Meeter;
import com.openmeet.shared.data.rating.Rating;
import com.openmeet.shared.data.storage.DAO;
import com.openmeet.shared.exceptions.InvalidPrimaryKeyException;
import com.openmeet.shared.helpers.ResponseHelper;
import com.openmeet.webservice.InvalidParameterException;
import jakarta.servlet.http.HttpServletRequest;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

public class RatingProxyDAO extends ProxyDAO<Rating> implements DAO<Rating> {


    public RatingProxyDAO(DAO<Rating> dao, HttpServletRequest request, PrintWriter out) {
        super(dao, request, out);
    }

    @Override
    public List<Rating> doRetrieveByCondition(String condition) throws SQLException {
        condition = request.getParameter("condition");

        logger.log(Level.INFO, "RatingProxyDAO:doRetrieveByCondition() - condition: " + condition);

        if (!ResponseHelper.checkStringFields(condition)) {
            throw new InvalidParameterException("Missing parameters - condition");
        }

        List<Rating> rates = dao.doRetrieveByCondition(condition);

        Gson builder = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        String json = builder.toJson(rates);

        HashMap<String, String> values = new HashMap<>();
        values.put("status", "success");
        values.put("data", json);
        ResponseHelper.sendGenericResponse(out, values);

        logger.log(Level.INFO, "RatingProxyDAO:doRetrieveByCondition() - rates: " + rates);

        return rates;
    }

    @Override
    public Rating doRetrieveByKey(String key) throws SQLException, InvalidPrimaryKeyException {

        key = request.getParameter("key");

        logger.log(Level.INFO, "RatingProxyDAO:doRetrieveByKey() - key: " + key);

        if (!ResponseHelper.checkStringFields(key)) {
            throw new InvalidParameterException("Missing parameters - key");
        }

        Rating rate = dao.doRetrieveByKey(key);

        Gson builder = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        String json = builder.toJson(rate);

        HashMap<String, String> values = new HashMap<>();
        values.put("status", "success");
        values.put("data", json);
        ResponseHelper.sendGenericResponse(out, values);

        logger.log(Level.INFO, "RatingProxyDAO:doRetrieveByKey() - rate: " + rate);

        return rate;
    }

    @Override
    public List<Rating> doRetrieveAll() throws SQLException {

        logger.log(Level.INFO, "RatingProxyDAO:doRetrieveAll()");

        List<Rating> rates = dao.doRetrieveByCondition("TRUE");

        Gson builder = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        String json = builder.toJson(rates);

        HashMap<String, String> values = new HashMap<>();
        values.put("status", "success");
        values.put("data", json);
        ResponseHelper.sendGenericResponse(out, values);

        logger.log(Level.INFO, "RatingProxyDAO:doRetrieveAll() - rates: " + rates);

        return rates;

    }

    @Override
    public List<Rating> doRetrieveAll(int row_count) throws SQLException, InvalidParameterException {

        row_count = Integer.parseInt(request.getParameter("row_count"));

        logger.log(Level.INFO, "RatingProxyDAO:doRetrieveAll() - row_count: " + row_count);

        if (row_count <= 0) {
            throw new InvalidParameterException("Invalid parameters - row count");
        }

        List<Rating> rates = dao.doRetrieveByCondition("TRUE LIMIT " + row_count);

        Gson builder = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        String json = builder.toJson(rates);

        HashMap<String, String> values = new HashMap<>();
        values.put("status", "success");
        values.put("data", json);
        ResponseHelper.sendGenericResponse(out, values);

        logger.log(Level.INFO, "RatingProxyDAO:doRetrieveAll() - rates: " + rates);

        return rates;

    }

    @Override
    public List<Rating> doRetrieveAll(int offset, int row_count) throws SQLException, InvalidParameterException {

        row_count = Integer.parseInt(request.getParameter("row_count"));
        offset = Integer.parseInt(request.getParameter("offset"));

        logger.log(Level.INFO, "RatingProxyDAO:doRetrieveAll() - offset: " + offset + ", row_count: " + row_count);

        if (row_count <= 0) {
            throw new InvalidParameterException("Invalid parameters - row count");
        }
        if (offset < 0) {
            throw new InvalidParameterException("Invalid parameters - offset");
        }

        List<Rating> rates = dao.doRetrieveByCondition("TRUE LIMIT " + offset + ", " + row_count);

        Gson builder = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        String json = builder.toJson(rates);

        HashMap<String, String> values = new HashMap<>();
        values.put("status", "success");
        values.put("data", json);
        ResponseHelper.sendGenericResponse(out, values);

        logger.log(Level.INFO, "RatingProxyDAO:doRetrieveAll() - meeters: " + rates);

        return rates;
    }

    @Override
    public boolean doSave(Rating obj) throws SQLException {

        Gson builder = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        obj = builder.fromJson(request.getParameter("rate"), Rating.class);

        logger.log(Level.INFO, "RatingProxyDAO:doSave() - obj: " + obj);

        System.out.println(obj.toHashMap());

        boolean isSuccessful = dao.doSave(obj);

        HashMap<String, String> values = new HashMap<>();
        values.put("status", "success");
        values.put("data", String.valueOf(isSuccessful));
        ResponseHelper.sendGenericResponse(out, values);

        logger.log(Level.INFO, "RatingProxyDAO:doSave() - isSuccessful: " + isSuccessful);

        return isSuccessful;

    }

    @Override
    public boolean doSave(HashMap<String, ?> values) throws SQLException {

        Gson builder = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        values = builder.fromJson(request.getParameter("values"), HashMap.class);

        logger.log(Level.INFO, "RatingProxyDAO:doSave() - values: " + values);

        boolean isSuccessful = dao.doSave(values);

        HashMap<String, String> val = new HashMap<>();
        val.put("status", "success");
        val.put("data", String.valueOf(isSuccessful));

        ResponseHelper.sendGenericResponse(out, val);

        logger.log(Level.INFO, "RatingProxyDAO:doSave() - isSuccessful: " + isSuccessful);

        return isSuccessful;

    }

    @Override
    public boolean doUpdate(HashMap<String, ?> values, String condition) throws SQLException {

        Gson builder = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        values = builder.fromJson(request.getParameter("values"), HashMap.class);
        condition = request.getParameter("condition");


        logger.log(Level.INFO, "RatingProxyDAO:doUpdate() - values: " + values + ", condition: " + condition);

        if (!ResponseHelper.checkStringFields(condition)) {
            throw new InvalidParameterException("Missing parameters - condition");
        }

        boolean isSuccessful = dao.doUpdate(values, condition);

        HashMap<String, String> val = new HashMap<>();
        val.put("status", "success");
        val.put("data", String.valueOf(isSuccessful));

        ResponseHelper.sendGenericResponse(out, val);

        logger.log(Level.INFO, "RatingProxyDAO:doUpdate() - isSuccessful: " + isSuccessful);

        return isSuccessful;

    }

    @Override
    public boolean doSaveOrUpdate(Rating obj) throws SQLException {

        Gson builder = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        obj = builder.fromJson(request.getParameter("rate"), Rating.class);

        logger.log(Level.INFO, "RatingProxyDAO:doSaveOrUpdate() - obj: " + obj);

        boolean isSuccessful = dao.doSaveOrUpdate(obj);

        HashMap<String, String> values = new HashMap<>();
        values.put("status", "success");
        values.put("data", String.valueOf(isSuccessful));

        ResponseHelper.sendGenericResponse(out, values);

        logger.log(Level.INFO, "RatingProxyDAO:doSaveOrUpdate() - isSuccessful: " + isSuccessful);

        return isSuccessful;
    }

    @Override
    public boolean doDelete(String condition) throws SQLException {

        condition = request.getParameter("condition");

        logger.log(Level.INFO, "RatingProxyDAO:doDelete() - condition: " + condition);

        boolean isSuccessful = dao.doDelete(condition);

        HashMap<String, String> values = new HashMap<>();
        values.put("status", "success");
        values.put("data", String.valueOf(isSuccessful));

        ResponseHelper.sendGenericResponse(out, values);

        logger.log(Level.INFO, "RatingProxyDAO:doDelete() - isSuccessful: " + isSuccessful);

        return isSuccessful;

    }
}
