package com.openmeet.shared.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class for programmatically constructing a SQL query.
 */
public class QueryBuilder {

    private final StringBuilder sqlBuilder;

    private QueryBuilder(StringBuilder sqlBuilder) {

        this.sqlBuilder = sqlBuilder;
    }

    /**
     * Creates a "select" query.
     *
     * @param columns The column names.
     * @return The new {@link QueryBuilder} instance.
     */

    public static QueryBuilder SELECT(String... columns) {

        if (columns == null) {

            throw new IllegalArgumentException();
        }

        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("select ");
        sqlBuilder.append(String.join(", ", columns));

        return new QueryBuilder(sqlBuilder);
    }

    /**
     * Appends a "from" clause to a query.
     *
     * @param tables The table names.
     * @return The {@link QueryBuilder} instance.
     */

    public QueryBuilder FROM(String... tables) {

        if (tables == null) {

            throw new IllegalArgumentException();
        }

        sqlBuilder.append(" from ");
        sqlBuilder.append(String.join(", ", tables));

        return this;
    }

    /**
     * Appends a "join" clause to a query.
     *
     * @param table The table name.
     * @return The {@link QueryBuilder} instance.
     */

    public QueryBuilder JOIN(String table) {

        if (table == null) {

            throw new IllegalArgumentException();
        }

        sqlBuilder.append(" join ");
        sqlBuilder.append(table);

        return this;
    }

    /**
     * Appends a "left join" clause to a query.
     *
     * @param table The table name.
     * @return The {@link QueryBuilder} instance.
     */

    public QueryBuilder LEFT_JOIN(String table) {

        if (table == null) {

            throw new IllegalArgumentException();
        }

        sqlBuilder.append(" left join ");
        sqlBuilder.append(table);

        return this;
    }

    /**
     * Appends a "right join" clause to a query.
     *
     * @param table The table name.
     * @return The {@link QueryBuilder} instance.
     */

    public QueryBuilder RIGHT_JOIN(String table) {

        if (table == null) {

            throw new IllegalArgumentException();
        }

        sqlBuilder.append(" right join ");
        sqlBuilder.append(table);

        return this;
    }

    /**
     * Appends an "on" clause to a query.
     *
     * @param predicate The predicate.
     * @return The {@link QueryBuilder} instance.
     */

    public QueryBuilder ON(String predicate) {

        if (predicate == null) {

            throw new IllegalArgumentException();
        }

        sqlBuilder.append(" on ");
        sqlBuilder.append(predicate);

        return this;
    }

    /**
     * Appends a "where" clause to a query.
     *
     * @param predicate The predicate.
     * @return The {@link QueryBuilder} instance.
     */

    public QueryBuilder WHERE(String predicate) {

        if (predicate == null) {

            throw new IllegalArgumentException();
        }

        sqlBuilder.append(" where ");
        sqlBuilder.append(predicate);

        return this;
    }

    /**
     * Appends an "order by" clause to a query.
     *
     * @param columns The column names.
     * @return The {@link QueryBuilder} instance.
     */

    public QueryBuilder ORDER_BY(String... columns) {

        if (columns == null) {

            throw new IllegalArgumentException();
        }

        sqlBuilder.append(" order by ");
        sqlBuilder.append(String.join(", ", columns));

        return this;
    }

    /**
     * Appends a "limit" clause to a query.
     *
     * @param row_count The limit count.
     * @return The {@link QueryBuilder} instance.
     */

    public QueryBuilder LIMIT(int row_count) {

        if (row_count < 0) {

            throw new IllegalArgumentException();
        }

        sqlBuilder.append(" limit ");
        sqlBuilder.append(row_count);

        return this;
    }

    /**
     * Appends a "limit" clause to a query.
     *
     * @param offset    Starting offset
     * @param row_count Ending offset
     * @return The {@link QueryBuilder} instance.
     */
    public QueryBuilder LIMIT(int offset, int row_count) {

        if (offset < 0 || row_count < 0) {

            throw new IllegalArgumentException();
        }

        sqlBuilder.append(" limit ");
        sqlBuilder.append(offset);
        sqlBuilder.append(", ");
        sqlBuilder.append(row_count);

        return this;
    }

    /**
     * Appends a "for update" clause to a query.
     *
     * @return The {@link QueryBuilder} instance.
     */

    public QueryBuilder FOR_UPDATE() {

        sqlBuilder.append(" for update");

        return this;
    }

    /**
     * Appends a "union" clause to a query.
     *
     * @param queryBuilder The query builder to append.
     * @return The {@link QueryBuilder} instance.
     */

    public QueryBuilder UNION(QueryBuilder queryBuilder) {

        sqlBuilder.append(" union ");
        sqlBuilder.append(queryBuilder.toString());

        return this;
    }

    /**
     * Creates an "insert into" query.
     *
     * @param table  The table name.
     * @param values The values to insert.
     * @return The new {@link QueryBuilder} instance.
     */

    public static QueryBuilder INSERT_INTO(String table, Map<String, ?> values) {

        if (table == null) {

            throw new IllegalArgumentException();
        }

        if (values == null) {

            throw new IllegalArgumentException();
        }

        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("insert into ");
        sqlBuilder.append(table);
        sqlBuilder.append(" (");

        List<String> columns = new ArrayList<>(values.keySet());

        int n = columns.size();

        for (int i = 0; i < n; i++) {

            if (i > 0) {

                sqlBuilder.append(", ");
            }

            sqlBuilder.append(columns.get(i));
        }

        sqlBuilder.append(") values (");

        for (int i = 0; i < n; i++) {

            if (i > 0) {

                sqlBuilder.append(", ");
            }

            Object value = values.get(columns.get(i));

            if (value instanceof QueryBuilder) {

                sqlBuilder.append("(");
                sqlBuilder.append(value);
                sqlBuilder.append(")");
            } else {

                sqlBuilder.append(encode(value));
            }
        }

        sqlBuilder.append(")");

        return new QueryBuilder(sqlBuilder);
    }

    /**
     * Creates an "update" query.
     *
     * @param table The table name.
     * @return The new {@link QueryBuilder} instance.
     */

    public static QueryBuilder UPDATE(String table) {

        if (table == null) {

            throw new IllegalArgumentException();
        }

        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("update ");
        sqlBuilder.append(table);

        return new QueryBuilder(sqlBuilder);
    }

    /**
     * Appends a "set" command to a query.
     *
     * @param values The values to update.
     * @return The {@link QueryBuilder} instance.
     */

    public QueryBuilder SET(Map<String, ?> values) {

        if (values == null) {

            throw new IllegalArgumentException();
        }

        sqlBuilder.append(" set ");

        int i = 0;

        for (Map.Entry<String, ?> entry : values.entrySet()) {

            if (i > 0) {

                sqlBuilder.append(", ");
            }

            sqlBuilder.append(entry.getKey());
            sqlBuilder.append(" = ");

            Object value = entry.getValue();

            if (value instanceof QueryBuilder) {

                sqlBuilder.append("(");
                sqlBuilder.append(value);
                sqlBuilder.append(")");
            } else {

                sqlBuilder.append(encode(value));
            }

            i++;
        }

        return this;
    }

    /**
     * Creates a "delete from" query.
     *
     * @param table The table name.
     * @return The new {@link QueryBuilder} instance.
     */

    public static QueryBuilder DELETE_FROM(String table) {

        if (table == null) {

            throw new IllegalArgumentException();
        }

        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("delete from ");
        sqlBuilder.append(table);

        return new QueryBuilder(sqlBuilder);
    }


    private static String encode(Object value) {

        if (value instanceof String string) {

            if (string.startsWith(":") || string.equals("?")) {

                return string;
            } else {

                return "'" +
                        string.replace("'", "''") +
                        "'";
            }
        } else {

            return String.valueOf(value);
        }
    }

    @Override
    public String toString() {

        return sqlBuilder.toString();
    }
}
