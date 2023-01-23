package com.openmeet.shared.data.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.openmeet.shared.data.meeter.Meeter;
import com.openmeet.shared.helpers.ResponseHelper;


import java.io.PrintWriter;
import java.security.InvalidParameterException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GenericProxyDAO {

    private final static Logger logger = Logger.getLogger(GenericProxyDAO.class.getName());

    private GenericProxyDAO() {
        //not accessible
    }

    public static <T> List<T> genericProxyDoRetrieveByCondition(String condition, DAO<T> dao, PrintWriter out) throws SQLException {

        List<T> entities = dao.doRetrieveByCondition(condition);

        Gson builder = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        String json = builder.toJson(entities);

        HashMap<String, String> values = new HashMap<>();
        values.put("status", "success");
        values.put("data", json);
        ResponseHelper.sendGenericResponse(out, values);

        logger.log(Level.INFO, "GenericProxyDAO:genericProxyDoRetrieveByCondition() - entities: " + entities);

        return entities;
    }

    public static <T> T genericProxyDoRetrieveByKey(String key, DAO<T> dao, PrintWriter out) throws SQLException {

        T entity = dao.doRetrieveByKey(key);

        Gson builder = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        String json = builder.toJson(entity);

        HashMap<String, String> values = new HashMap<>();
        values.put("status", "success");
        values.put("data", json);
        ResponseHelper.sendGenericResponse(out, values);

        logger.log(Level.INFO, "MeeterProxyDAO:doRetrieveByKey() - entity: " + entity);

        return entity;
    }

    public static <T> boolean genericProxyDoSave(T entity, DAO<T> dao, PrintWriter out) throws SQLException {

        boolean result = dao.doSave(entity);

        HashMap<String, String> values = new HashMap<>();
        values.put("status", "success");
        values.put("data", String.valueOf(result));
        ResponseHelper.sendGenericResponse(out, values);

        logger.log(Level.INFO, "GenericProxyDAO:genericProxyDoSave() - result: " + result);

        return result;
    }

    public static <T> boolean genericProxyDoSave(HashMap<String, ?> values, DAO<T> dao, PrintWriter out) throws SQLException {

        boolean result = dao.doSave(values);

        HashMap<String, String> val = new HashMap<>();
        val.put("status", "success");
        val.put("data", String.valueOf(result));
        ResponseHelper.sendGenericResponse(out, val);

        logger.log(Level.INFO, "GenericProxyDAO:genericProxyDoSave() - result: " + result);

        return result;
    }

    public static <T> boolean genericProxyDoUpdate(HashMap<String, ?> values, String condition, DAO<T> dao, PrintWriter out) throws SQLException {

        boolean result = dao.doUpdate(values, condition);

        HashMap<String, String> val = new HashMap<>();
        val.put("status", "success");
        val.put("data", String.valueOf(result));
        ResponseHelper.sendGenericResponse(out, val);

        logger.log(Level.INFO, "GenericProxyDAO:genericProxyDoUpdate() - result: " + result);

        return result;
    }

    public static <T> boolean genericProxyDoSaveOrUpdate(T entity, DAO<T> dao, PrintWriter out) throws SQLException {

        boolean result = dao.doSaveOrUpdate(entity);

        HashMap<String, String> values = new HashMap<>();
        values.put("status", "success");
        values.put("data", String.valueOf(result));
        ResponseHelper.sendGenericResponse(out, values);

        logger.log(Level.INFO, "GenericProxyDAO:genericProxyDoSave() - result: " + result);

        return result;
    }

    public static <T> boolean genericProxyDoDelete(String condition, DAO<T> dao, PrintWriter out) throws SQLException {

        boolean result = dao.doDelete(condition);

        HashMap<String, String> values = new HashMap<>();
        values.put("status", "success");
        values.put("data", String.valueOf(result));
        ResponseHelper.sendGenericResponse(out, values);

        logger.log(Level.INFO, "GenericProxyDAO:genericProxyDoSave() - result: " + result);

        return result;
    }
}
