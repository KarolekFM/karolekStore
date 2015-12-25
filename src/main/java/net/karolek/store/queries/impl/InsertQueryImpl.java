package net.karolek.store.queries.impl;

import lombok.Getter;
import net.karolek.store.Store;
import net.karolek.store.queries.InsertQuery;
import net.karolek.store.queries.columns.StoreColumn;
import net.karolek.store.queries.columns.StoreColumnImpl;
import net.karolek.store.runner.PreparedQuery;
import net.karolek.store.runner.QueryExecutor;

import java.util.HashMap;
import java.util.Map;

@Getter
public class InsertQueryImpl extends StoreQueryImpl<InsertQuery> implements InsertQuery {

    private final InsertQuery instance;

    private Map<StoreColumn, String> adds = new HashMap<>();

    public InsertQueryImpl() {
        this.instance = this;
    }

    @Override
    public InsertQuery add(StoreColumn column, String value) {
        adds.put(column, value);
        return this;
    }

    @Override
    public InsertQuery add(String column, String value) {
        adds.put(new StoreColumnImpl(column), value);
        return this;
    }

    @Override
    public InsertQuery add(String tablePrefix, String column, String value) {
        adds.put(new StoreColumnImpl(column, tablePrefix), value);
        return this;
    }

    @Override
    public InsertQuery add(StoreColumn column, int value) {
        adds.put(column, String.valueOf(value));
        return this;
    }

    @Override
    public InsertQuery add(String column, int value) {
        adds.put(new StoreColumnImpl(column), String.valueOf(value));
        return this;
    }

    @Override
    public InsertQuery add(String tablePrefix, String column, int value) {
        adds.put(new StoreColumnImpl(column, tablePrefix), String.valueOf(value));
        return this;
    }

    @Override
    public void execute(Store store) {
        if (getTable() == null) throw new IllegalArgumentException();
        StringBuilder sb = new StringBuilder();

        sb.append("INSERT INTO ").append(getTable()).append(" (");

        boolean first = true;
        for (StoreColumn c : adds.keySet()) {
            if (!first) sb.append(", ");
            sb.append(c.getString());
            first = false;
        }
        sb.append(") VALUES (");

        first = true;
        for (String s : adds.values()) {
            if (!first) sb.append(", ");
            sb.append("'").append(s).append("'");
            first = false;
        }
        sb.append(")");

        System.out.println(sb.toString());

        store.runQuery(new PreparedQuery(sb.toString(), callback, now, QueryExecutor.UPDATE));

    }
}
