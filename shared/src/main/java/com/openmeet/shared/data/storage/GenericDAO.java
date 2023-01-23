package com.openmeet.shared.data.storage;

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

public final class GenericDAO {

    private final static Logger logger = Logger.getLogger(DAO.class.getName());

    private GenericDAO() {
        //not accessible
    }

    /**
     * Returns a list of objects from the database that match a given condition.
     *
     * @param table the name of the table in the database from which the records are to be retrieved.
     * @param condition the condition that the records should match in order to be retrieved.
     * @param extractor an instance of a class that implements the ResultSetExtractor interface.
     * The extractor is used to convert the ResultSet returned by the database query into a
     * List of objects of the desired type.
     * @param source an instance of DataSource, an object used to establish a connection to the database.
     * @return the query results as a list of objects.
     *
     * @author Francesco Granozio
     * @author Angelo Nazzaro
     */
    public static <E extends ResultSetExtractor<T>, T> List<T> genericDoRetrieveByCondition(String table, String condition, E extractor, DataSource source) throws SQLException {

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

    /**
     * Saves an object in the database.
     *
     * @param table the name of the table in the database from which the records are to be retrieved.
     * @param map a HashMap representing the data to be saved, where the keys are the column
     * names and the values are the values to be inserted.
     * @param source an instance of DataSource, an object used to establish a connection to the database.
     * @return a boolean value that indicates if the operation was successful or not.
     *
     * @author Francesco Granozio
     * @author Angelo Nazzaro
     */
    public static boolean genericDoSave(String table, HashMap<String, ?> map, DataSource source) throws SQLException {

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

    /**
     * Updates an object in the database.
     *
     * @param table the name of the table in the database from which the records are to be retrieved.
     * @param condition the condition that the records should match in order to be retrieved.
     * @param values a HashMap representing the data to be updated, where the keys are the
     * column names and the values are the new values to be set.
     * @param source an instance of DataSource, an object used to establish a connection to the database.
     * @return a boolean value that indicates if the operation was successful or not.
     *
     * @author Francesco Granozio
     * @author Angelo Nazzaro
     */

    public static boolean genericDoUpdate(String table, String condition, HashMap<String, ?> values, DataSource source) throws SQLException {

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

    /**
     * Deletes an object in the database.
     *
     * @param table the name of the table in the database from which the records are to be retrieved.
     * @param condition the condition that the records should match in order to be retrieved.
     * @param source an instance of DataSource, an object used to establish a connection to the database.
     * @return a boolean value that indicates if the operation was successful or not.
     *
     * @author Francesco Granozio
     * @author Angelo Nazzaro
     */

    public static boolean genericDoDelete(String table, String condition, DataSource source) throws SQLException {

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
