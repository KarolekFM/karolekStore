package net.karolek.store.queries.impl;

import net.karolek.store.Store;
import net.karolek.store.common.PreparedQuery;
import net.karolek.store.common.runner.QueryExecutor;
import net.karolek.store.queries.CustomQuery;

public class CustomQueryImpl extends StoreQueryImpl<CustomQuery> implements CustomQuery {

    private String query;

    @Override
    public CustomQuery query(String query) {
        this.query = query;
        return this;
    }

    @Override
    public void execute(Store store) {
        if (query == null || "".equalsIgnoreCase(query)) throw new IllegalArgumentException();
        store.runQuery(new PreparedQuery(query, callback, now, query.startsWith("SELECT") ? QueryExecutor.SELECT : QueryExecutor.UPDATE));
    }
}
