package net.karolek.store.modes;

import net.karolek.store.Store;
import net.karolek.store.runner.TaskProvider;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

public class StoreSqlite extends Store {

    private final String filename;

    public StoreSqlite(TaskProvider taskProvider, String filename) {
        super(StoreMode.SQLITE, taskProvider);
        this.filename = filename;
        connect();
    }

    @Override
    public void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + filename);
            getTaskProvider().getLogger().log(Level.INFO, "Database connection has been established");
        } catch (ClassNotFoundException e) {
            getTaskProvider().getLogger().log(Level.WARNING, "JDBC driver not found!", "Error: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            getTaskProvider().getLogger().log(Level.WARNING, "Can not connect to a SQLite server!", "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void disconnect() {
        if (!isConnected()) return;
        try {
            this.connection.close();
        } catch (SQLException e) {
            getTaskProvider().getLogger().log(Level.WARNING, "Can not close the connection to the Sqlite server!", "Error: " + e.getMessage());
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
}
