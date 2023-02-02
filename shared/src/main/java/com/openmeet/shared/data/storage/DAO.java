package com.openmeet.shared.data.storage;

import com.openmeet.shared.exceptions.InvalidPrimaryKeyException;
import com.openmeet.shared.utils.QueryBuilder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

/**
 * It contains all the methods that should be implemented
 * by the DAOs.
 *
 * @author Francesco Granozio
 * @author Angelo Nazzaro
 */
public interface DAO<T> {

    String DO_RETRIEVE_BY_CONDITION = "doRetrieveByCondition";
    String DO_RETRIEVE_BY_CONDITION_LIMIT = "doRetrieveByConditionLimit";
    String DO_RETRIEVE_BY_KEY = "doRetrieveByKey";
    String DO_RETRIEVE_ALL = "doRetrieveAll";
    String DO_RETRIEVE_ALL_LIMIT = "doRetrieveAllLimit";
    String DO_RETRIEVE_ALL_LIMIT_OFFSET = "doRetrieveAllLimitOffset";
    String DO_SAVE = "doSave";
    String DO_SAVE_PARTIAL = "doSavePartial";
    String DO_UPDATE = "doUpdate";
    String DO_SAVE_OR_UPDATE = "doSaveOrUpdate";
    String DO_DELETE = "doDelete";

    Logger logger = Logger.getLogger(DAO.class.getName());

    /**
     * Returns a list of objects from the database that match a given condition.
     *
     * @param condition the condition to be matched.
     * @return the query results as a list of objects.
     *
     * @author Francesco Granozio
     * @author Angelo Nazzaro
     */
    List<T> doRetrieveByCondition(String condition) throws SQLException;

    /**
     * Returns a list of objects from the database that match a given condition.
     *
     * @param condition the condition to be matched.
     * @param offset the offset position.
     * @param rows_count the number of rows to return.
     * @return the query results as a list of objects.
     *
     * @author Angelo Nazzaro
     */
    List<T> doRetrieveByCondition(String condition, int offset, int rows_count) throws SQLException;

    /**
     * Returns an object from the database that match a given condition.
     *
     * @param key the primary key of the object to be retrieved.
     * @return the query result as a single object
     *
     * @author Francesco Granozio
     * @author Angelo Nazzaro
     */
    T doRetrieveByKey(String key) throws SQLException, InvalidPrimaryKeyException;

    /**
     * Returns a list of all objects from the database.
     *
     * @return the query results as a list of objects.
     *
     * @author Francesco Granozio
     * @author Angelo Nazzaro
     */
    List<T> doRetrieveAll() throws SQLException;

    /**
     * Returns a list of objects from the database,
     * limiting the result set to the specified number of rows.
     *
     * @param row_count the number of rows to be returned.
     * @return the query results as a list of objects.
     *
     * @author Francesco Granozio
     * @author Angelo Nazzaro
     */
    List<T> doRetrieveAll(int row_count) throws SQLException;

    /**
     * Returns a list of objects from the database, starting at a specified offset
     * and limiting the result set to the specified number of rows.
     *
     * @param offset the offset to start the query from.
     * @param row_count the number of rows to be returned.
     * @return the query results as a list of objects.
     *
     * @author Francesco Granozio
     * @author Angelo Nazzaro
     */
    List<T> doRetrieveAll(int offset, int row_count) throws SQLException;

    /**
     * Saves an object in the database.
     *
     * @param obj the object to be saved.
     * @return a boolean value that indicates if the operation was successful or not.
     *
     * @author Francesco Granozio
     * @author Angelo Nazzaro
     */
    boolean doSave(T obj) throws SQLException;

    /**
     * Saves an object in the database populating only the fields contained
     * in values.
     *
     * @param values the values to be updated.
     * @return a boolean value that indicates if the operation was successful or not.
     *
     * @author Francesco Granozio
     * @author Angelo Nazzaro
     */
    boolean doSave(HashMap<String, ?> values) throws SQLException;

    /**
     * Updates database objects that match a given condition,
     * using only the values contained in the HashMap
     *
     * @param values the values to be updated.
     * @param condition the condition to be matched.
     * @return a boolean value that indicates if the operation was successful or not.
     *
     * @author Francesco Granozio
     * @author Angelo Nazzaro
     */
    boolean doUpdate(HashMap<String, ?> values, String condition) throws SQLException;

    /**
     * Saves an object to the database if it does not already exist, or updates it if it does.
     *
     * @param obj the object to be saved or updated.
     * @return a boolean value that indicates if the operation was successful or not.
     *
     * @author Francesco Granozio
     * @author Angelo Nazzaro
     */
    boolean doSaveOrUpdate(T obj) throws SQLException;

    /**
     * Deletes objects from the database that match a given condition.
     *
     * @param condition the condition to be matched.
     * @return a boolean value that indicates if the operation was successful or not.
     *
     * @author Francesco Granozio
     * @author Angelo Nazzaro
     */
    boolean doDelete(String condition) throws SQLException;
}