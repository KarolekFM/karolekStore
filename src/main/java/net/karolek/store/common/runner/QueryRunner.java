package net.karolek.store.common.runner;

import lombok.Getter;
import lombok.Setter;
import net.karolek.store.Store;
import net.karolek.store.common.PreparedQuery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

@Getter
@Setter
public class QueryRunner implements Runnable {

    private final Store store;
    private final PreparedQuery preparedQuery;

    public QueryRunner(Store store, PreparedQuery preparedQuery) {
        this.store = store;
        this.preparedQuery = preparedQuery;
    }

    @Override
    public void run() {
        if (!store.isConnected()) throw new IllegalArgumentException("no connection");

        if (store.isDebug())
            store.getTaskProvider().getLogger().log(Level.INFO, "[DEBUG] Run sql query: {" + getPreparedQuery().getQuery() + "}");

        try {
            Statement statement = store.getConnection().createStatement();
            ResultSet rs = getPreparedQuery().getQueryExecutor().runQuery(statement, getPreparedQuery().getQuery());
            if (rs != null && getPreparedQuery().getCallback() != null) {
                getPreparedQuery().getCallback().done(rs);
            }
        } catch (SQLException e) {
            if (getPreparedQuery().getCallback() != null) {
                getPreparedQuery().getCallback().error(e);
            } else {
                e.printStackTrace();
            }
        }

    }
}
