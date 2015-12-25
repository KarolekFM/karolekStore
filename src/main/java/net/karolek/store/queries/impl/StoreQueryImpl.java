package net.karolek.store.queries.impl;

import lombok.Getter;
import net.karolek.store.Store;
import net.karolek.store.queries.StoreQuery;
import net.karolek.store.queries.tables.StoreTable;
import net.karolek.store.queries.tables.StoreTableImpl;
import net.karolek.store.runner.QueryCallback;

@Getter
public abstract class StoreQueryImpl<T extends StoreQuery> implements StoreQuery<T> {

    @Getter
    protected StoreTable table;
    protected boolean now;
    protected QueryCallback callback;

    @Override
    public T table(StoreTable storeTable) {
        this.table = storeTable;
        return (T) this;
    }

    @Override
    public T table(String tableName) {
        this.table = new StoreTableImpl(tableName);
        return (T) this;
    }

    @Override
    public T table(String tablePrefix, String tableName) {
        this.table = new StoreTableImpl(tableName, tablePrefix);
        return (T) this;
    }

    @Override
    public T now(boolean now) {
        this.now = now;
        return (T) this;
    }

    @Override
    public T callback(QueryCallback queryCallback) {
        this.callback = queryCallback;
        return (T) this;
    }

    @Override
    public abstract void execute(Store store);


}
