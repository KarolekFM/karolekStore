package net.karolek.store.common.runner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public enum QueryExecutor {

    SELECT {
        @Override
        public ResultSet runQuery(Statement statement, String query) throws SQLException {
            return statement.executeQuery(query);
        }
    },
    UPDATE {
        @Override
        public ResultSet runQuery(Statement statement, String query) throws SQLException {
            statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            return statement.getGeneratedKeys();
        }
    };

    public abstract ResultSet runQuery(Statement statement, String query) throws SQLException;

}
