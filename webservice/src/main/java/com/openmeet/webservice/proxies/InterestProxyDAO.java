package com.openmeet.webservice.proxies;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.openmeet.shared.data.interest.Interest;
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

/**
 * Proxy class for InterestDAO.
 *
 * @author Francesco Granozio
 * @see ProxyDAO
 * @see DAO
 * @see GenericProxyDAO
 */
public class InterestProxyDAO extends ProxyDAO<Interest> implements DAO<Interest> {

    public InterestProxyDAO(DAO<Interest> dao, HttpServletRequest request, PrintWriter out) {
        super(dao, request, out);
    }

    @Override
    public List<Interest> doRetrieveByCondition(String condition) throws SQLException {

        condition = request.getParameter("condition");

        if (!ResponseHelper.checkStringFields(condition)) {
            throw new InvalidParameterException("Missing parameters - condition");
        }

        logger.log(Level.INFO, "InterestProxyDAO:doRetrieveByCondition() - condition: " + condition);

        List<Interest> interests = GenericProxyDAO.genericProxyDoRetrieveByCondition(condition, dao, out);

        logger.log(Level.INFO, "InterestProxyDAO:doRetrieveByCondition() - interest: " + interests);

        return interests;

    }

    @Override
    public List<Interest> doRetrieveByCondition(String condition, int row_count) throws SQLException {

        condition = request.getParameter("condition");
        row_count = Integer.parseInt(request.getParameter("row_count"));

        if (!ResponseHelper.checkStringFields(condition)) {
            throw new InvalidParameterException("Missing parameters - condition");
        }

        if (row_count <= 0) {
            throw new InvalidParameterException("Rows_count parameter must be greater than 0");
        }

        logger.log(Level.INFO, "InterestProxyDAO:doRetrieveByCondition() - condition: " + condition + " LIMIT " + row_count);

        List<Interest> interests = GenericProxyDAO.genericProxyDoRetrieveByCondition(condition, 0, row_count, dao, out);

        logger.log(Level.INFO, "InterestProxyDAO:doRetrieveByCondition() - interest: " + interests);

        return interests;

    }

    @Override
    public List<Interest> doRetrieveByCondition(String condition, int offset, int row_count) throws SQLException {

        condition = request.getParameter("condition");
        offset = Integer.parseInt(request.getParameter("offset"));
        row_count = Integer.parseInt(request.getParameter("row_count"));

        if (!ResponseHelper.checkStringFields(condition)) {
            throw new InvalidParameterException("Missing parameters - condition");
        }

        if (offset < 0) {
            throw new InvalidParameterException("Offset parameter cannot contain a negative value");
        }

        if (row_count <= 0) {
            throw new InvalidParameterException("Rows_count parameter must be greater than 0");
        }

        logger.log(Level.INFO, "InterestProxyDAO:doRetrieveByCondition() - condition: " + condition + " LIMIT " + offset + ", " + row_count);

        List<Interest> interests = GenericProxyDAO.genericProxyDoRetrieveByCondition(condition, offset, row_count, dao, out);

        logger.log(Level.INFO, "InterestProxyDAO:doRetrieveByCondition() - interest: " + interests);

        return interests;

    }

    @Override
    public Interest doRetrieveByKey(String key) throws SQLException, InvalidPrimaryKeyException {

        key = request.getParameter("key");

        if (!ResponseHelper.checkStringFields(key)) {
            throw new InvalidParameterException("Missing parameters - key");
        }

        logger.log(Level.INFO, "InterestProxyDAO:doRetrieveByKey() - key: " + key);

        Interest interest = GenericProxyDAO.genericProxyDoRetrieveByKey(key, dao, out);

        logger.log(Level.INFO, "InterestProxyDAO:doRetrieveByKey() - interest: " + interest);

        return interest;

    }

    @Override
    public List<Interest> doRetrieveAll() throws SQLException {

        logger.log(Level.INFO, "InterestProxyDAO:doRetrieveAll()");

        List<Interest> interests = GenericProxyDAO.genericProxyDoRetrieveByCondition("TRUE", dao, out);

        logger.log(Level.INFO, "InterestProxyDAO:doRetrieveAll() - interests: " + interests);

        return interests;

    }

    @Override
    public List<Interest> doRetrieveAll(int row_count) throws SQLException {

        row_count = Integer.parseInt(request.getParameter("row_count"));

        logger.log(Level.INFO, "InterestProxyDAO:doRetrieveAll() - row_count: " + row_count);

        if (row_count <= 0) {
            throw new InvalidParameterException("Invalid parameters - row count");
        }

        List<Interest> interests = GenericProxyDAO.genericProxyDoRetrieveByCondition("TRUE LIMIT " + row_count, dao, out);

        logger.log(Level.INFO, "InterestProxyDAO:doRetrieveAll() - interests: " + interests);

        return interests;

    }

    @Override
    public List<Interest> doRetrieveAll(int offset, int row_count) throws SQLException {

        row_count = Integer.parseInt(request.getParameter("row_count"));
        offset = Integer.parseInt(request.getParameter("offset"));

        logger.log(Level.INFO, "InterestProxyDAO:doRetrieveAll() - offset: " + offset + ", row_count: " + row_count);

        if (row_count <= 0) {
            throw new InvalidParameterException("Invalid parameters - row count");
        }
        if (offset < 0) {
            throw new InvalidParameterException("Invalid parameters - offset");
        }

        List<Interest> interests = GenericProxyDAO.genericProxyDoRetrieveByCondition("TRUE LIMIT " + offset + ", " + row_count, dao, out);

        logger.log(Level.INFO, "InterestProxyDAO:doRetrieveAll() - interests: " + interests);

        return interests;

    }

    @Override
    public boolean doSave(Interest obj) throws SQLException {

        Gson builder = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        obj = builder.fromJson(request.getParameter("interest"), Interest.class);

        logger.log(Level.INFO, "InterestProxyDAO:doSave() - obj: " + obj);

        boolean isSuccessful = GenericProxyDAO.genericProxyDoSave(obj, dao, out);

        logger.log(Level.INFO, "InterestProxyDAO:doSave() - isSuccessful: " + isSuccessful);

        return isSuccessful;

    }

    @Override
    public boolean doSave(HashMap<String, ?> values) throws SQLException {

        Gson builder = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        values = builder.fromJson(request.getParameter("values"), HashMap.class);

        logger.log(Level.INFO, "InterestProxyDAO:doSave() - values: " + values);

        boolean isSuccessful = GenericProxyDAO.genericProxyDoSave(values, dao, out);

        logger.log(Level.INFO, "InterestProxyDAO:doSave() - isSuccessful: " + isSuccessful);

        return isSuccessful;

    }

    @Override
    public boolean doUpdate(HashMap<String, ?> values, String condition) throws SQLException {

        Gson builder = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        values = builder.fromJson(request.getParameter("values"), HashMap.class);
        condition = request.getParameter("condition");

        logger.log(Level.INFO, "InterestProxyDAO:doUpdate() - values: " + values + ", condition: " + condition);

        if (!ResponseHelper.checkStringFields(condition)) {
            throw new InvalidParameterException("Missing parameters - condition");
        }

        boolean isSuccessful = GenericProxyDAO.genericProxyDoUpdate(values, condition, dao, out);

        logger.log(Level.INFO, "InterestProxyDAO:doUpdate() - isSuccessful: " + isSuccessful);

        return isSuccessful;

    }

    @Override
    public boolean doSaveOrUpdate(Interest obj) throws SQLException {

        Gson builder = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        obj = builder.fromJson(request.getParameter("interest"), Interest.class);

        logger.log(Level.INFO, "InterestProxyDAO:doSaveOrUpdate() - obj: " + obj);

        boolean isSuccessful = GenericProxyDAO.genericProxyDoSaveOrUpdate(obj, dao, out);

        logger.log(Level.INFO, "InterestProxyDAO:doSaveOrUpdate() - isSuccessful: " + isSuccessful);

        return isSuccessful;

    }

    @Override
    public boolean doDelete(String condition) throws SQLException {

        condition = request.getParameter("condition");

        logger.log(Level.INFO, "InterestProxyDAO:doDelete() - condition: " + condition);

        boolean isSuccessful = GenericProxyDAO.genericProxyDoDelete(condition, dao, out);

        logger.log(Level.INFO, "InterestProxyDAO:doDelete() - isSuccessful: " + isSuccessful);

        return isSuccessful;

    }
}
