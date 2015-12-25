package net.karolek.store;

import lombok.Getter;
import lombok.Setter;
import net.karolek.store.common.PreparedQuery;
import net.karolek.store.common.TaskProvider;
import net.karolek.store.common.runner.QueryRunner;
import net.karolek.store.modes.StoreMode;
import net.karolek.store.modes.StoreMysql;
import net.karolek.store.modes.StoreSqlite;

import java.sql.Connection;

@Getter
@Setter
public abstract class Store {

    public static Store createMysql(TaskProvider taskProvider, String hostline, String basename, String username, String password) {
        return new StoreMysql(taskProvider, hostline, basename, username, password);
    }

    public static Store createSqlite(TaskProvider taskProvider, String filename) {
        return new StoreSqlite(taskProvider, filename);
    }

    private final StoreMode storeMode;
    private final TaskProvider taskProvider;
    private boolean debug = false;
    protected Connection connection;


    protected Store(StoreMode storeMode, TaskProvider taskProvider) {
        this.storeMode = storeMode;
        this.taskProvider = taskProvider;
    }

    public final void runQuery(PreparedQuery query) {
        Runnable runnable = new QueryRunner(this, query);

        if (query.isNow()) {
            runnable.run();
        } else {
            taskProvider.runTask(runnable);
        }
    }

    public abstract void connect();

    public abstract void disconnect();

    public abstract boolean isConnected();

}
