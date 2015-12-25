package net.karolek.store.modes;


import net.karolek.store.Store;
import net.karolek.store.runner.TaskProvider;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

public class StoreMysql extends Store {

    private final String hostline, basename, username, password;

    public StoreMysql(TaskProvider taskProvider, String hostline, String basename, String username, String password) {
        super(StoreMode.MYSQL, taskProvider);
        this.hostline = hostline;
        this.basename = basename;
        this.username = username;
        this.password = password;
        getTaskProvider().runTaskTimer(new KeepAliveTask(), 100 * 60);
        connect();
    }

    @Override
    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection("jdbc:mysql://" + hostline + "/" + basename, username, password);
            getTaskProvider().getLogger().log(Level.INFO, "Database connection has been established");
        } catch (ClassNotFoundException e) {
            getTaskProvider().getLogger().log(Level.WARNING, "JDBC driver not found!", "Error: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            getTaskProvider().getLogger().log(Level.WARNING, "Can not connect to a MySQL server!", "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void disconnect() {
        if (!isConnected()) return;
        try {
            this.connection.close();
        } catch (SQLException e) {
            getTaskProvider().getLogger().log(Level.WARNING, "Can not close the connection to the MySQL server!", "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public boolean isConnected() {
        try {
            return (!this.connection.isClosed()) || (this.connection == null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private class KeepAliveTask implements Runnable {

        @Override
        public void run() {
            try {
                getConnection().createStatement().executeUpdate("DO 1");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
