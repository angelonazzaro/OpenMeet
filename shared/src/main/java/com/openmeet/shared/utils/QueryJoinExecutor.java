package com.openmeet.shared.utils;

import com.openmeet.shared.data.storage.SQLDAO;

import javax.sql.DataSource;
import java.sql.*;
import java.util.logging.Logger;

/**
 * This class is used to execute JOIN Queries.
 * It uses the MultiMapList class.
 *
 * @see MultiMapList
 *
 * @author Francesco Granozio
 * @author Angelo Nazzaro
 * */
public class QueryJoinExecutor extends SQLDAO {

    Logger logger = Logger.getLogger(QueryJoinExecutor.class.getName());
    public QueryJoinExecutor(DataSource source) { super(source); }

    /**
     * Returns a MultiMapList object that can be used to handle Result Set objects
     * made up of different Beans.
     *
     * @param query the query join to be executed.
     * @return the query results as a MultiMapList.
     *
     * @see ResultSet
     * @see MultiMapList
     *
     * @author Francesco Granozio
     * @author Angelo Nazzaro
     * */
    public MultiMapList<String, String> doRetrivedByJoin(String query) throws SQLException {
        MultiMapList<String, String> data = new MultiMapList<>();

        try (Connection conn = source.getConnection()) {

            logger.info("[DO-RETRIEVE-BY-JOIN] " + query);

            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ResultSet set = ps.executeQuery();
                ResultSetMetaData metaData;
                int columnsNumber;

                while (set.next()) {
                    metaData = set.getMetaData();
                    columnsNumber = metaData.getColumnCount();

                    for (int i = 1; i <= columnsNumber; i++) {
                        data.addEntryInCurrentRow(metaData.getColumnName(i), set.getString(i));
                    }

                    data.addCurrentRow();
                }
            }
        }

        return data;
    }
}
