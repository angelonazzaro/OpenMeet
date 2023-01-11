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

    Logger logger = Logger.getLogger(DAO.class.getName());

    /**
     * Returns a list of objects from the database that match a given condition.
     *
     * @return the query results as a list of objects.
     * @author Francesco Granozio
     * @author Angelo Nazzaro
     */
    List<T> doRetrieveByCondition(String condition) throws SQLException;

    /**
     * Returns an object from the database that match a given condition.
     *
     * @return the query result as a single object
     * @author Francesco Granozio
     * @author Angelo Nazzaro
     */
    T doRetrieveByKey(String key) throws SQLException, InvalidPrimaryKeyException;

    /**
     * Returns a list of all objects from the database.
     *
     * @return the query results as a list of objects.
     * @author Francesco Granozio
     * @author Angelo Nazzaro
     */
    List<T> doRetrieveAll() throws SQLException;

    /**
     * Returns a list of objects from the database,
     * limiting the result set to the specified number of rows.
     *
     * @return the query results as a list of objects.
     * @author Francesco Granozio
     * @author Angelo Nazzaro
     */
    List<T> doRetrieveAll(int row_count) throws SQLException;

    /**
     * Returns a list of objects from the database, starting at a specified offset
     * and limiting the result set to the specified number of rows.
     *
     * @return the query results as a list of objects.
     * @author Francesco Granozio
     * @author Angelo Nazzaro
     */
    List<T> doRetrieveAll(int offset, int row_count) throws SQLException;

    /**
     * Saves an object in the database.
     *
     * @return a boolean value that indicates if the operation was successful or not.
     * @author Francesco Granozio
     * @author Angelo Nazzaro
     */
    boolean doSave(T obj) throws SQLException;

    /**
     * Updates database objects that match a given condition,
     * using only the values contained in the HashMap
     *
     * @return a boolean value that indicates if the operation was successful or not.
     * @author Francesco Granozio
     * @author Angelo Nazzaro
     */
    boolean doUpdate(HashMap<String, ?> values, String condition) throws SQLException;

    /**
     * Saves an object to the database if it does not already exist, or updates it if it does.
     *
     * @return a boolean value that indicates if the operation was successful or not.
     * @author Francesco Granozio
     * @author Angelo Nazzaro
     */
    boolean doSaveOrUpdate(T obj) throws SQLException;

    /**
     * Deletes objects from the database that match a given condition.
     *
     * @return a boolean value that indicates if the operation was successful or not.
     * @author Francesco Granozio
     * @author Angelo Nazzaro
     */
    boolean doDelete(String condition) throws SQLException;

    default <E extends ResultSetExtractor<T>> List<T> genericDoRetrieveByCondition(String table, String condition, E extractor, DataSource source) throws SQLException {

        final List<T> entity = new ArrayList<>();

        try (Connection conn = source.getConnection()) {

            String query = QueryBuilder.SELECT("*").FROM(table).WHERE(condition).toString();

            logger.info("[GENERIC-DO-RETRIEVE-BY-CONDITION] " + query);

            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ResultSet set = ps.executeQuery();

                while (set.next()) {
                    entity.add(extractor.extract(set));
                }
            }
        }
        return entity;
    }

    default boolean genericDoSave(String table, HashMap<String, ?> map, DataSource source) throws SQLException {

        int rows;
        try (Connection conn = source.getConnection()) {

            String query = QueryBuilder.INSERT_INTO(table, map).toString();

            logger.info("[GENERIC-DO-SAVE] " + query);

            try (PreparedStatement ps = conn.prepareStatement(query)) {

                rows = ps.executeUpdate();
            }
        }
        return rows == 1;
    }

    default boolean genericDoUpdate(String table, String condition, HashMap<String, ?> values, DataSource source) throws SQLException {

        int rows;
        try (Connection conn = source.getConnection()) {

            String query = QueryBuilder.UPDATE(table).SET(values).WHERE(condition).toString();

            logger.info("[GENERIC-DO-UPDATE] " + query);

            try (PreparedStatement ps = conn.prepareStatement(query)) {

                rows = ps.executeUpdate();
            }
        }
        return rows > 0;
    }

    default boolean genericDoDelete(String table, String condition, DataSource source) throws SQLException {

        int rows;
        try (Connection conn = source.getConnection()) {

            String query = QueryBuilder.DELETE_FROM(table).WHERE(condition).toString();

            logger.info("[GENERIC-DO-DELETE] " + query);

            try (PreparedStatement ps = conn.prepareStatement(query)) {

                ps.execute("SET FOREIGN_KEY_CHECKS = 0;");
                rows = ps.executeUpdate();
                ps.execute("SET FOREIGN_KEY_CHECKS = 1;");
            }
        }
        return rows > 0;
    }
}