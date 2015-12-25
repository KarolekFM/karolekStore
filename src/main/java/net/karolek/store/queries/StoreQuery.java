package net.karolek.store.queries;

import net.karolek.store.queries.tables.StoreTable;
import net.karolek.store.runner.QueryCallback;

public interface StoreQuery<T extends StoreQuery> extends ExecuteQuery {

    T table(StoreTable storeTable);

    T table(String tableName);

    T table(String tablePrefix, String tableName);

    T now(boolean now);

    T callback(QueryCallback queryCallback);

}
