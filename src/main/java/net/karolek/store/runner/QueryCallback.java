package net.karolek.store.runner;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface QueryCallback {

    void done(ResultSet resultSet) throws SQLException;

    void error(Throwable throwable);

}
