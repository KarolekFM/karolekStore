package net.karolek.store.queries.impl;

import lombok.Getter;
import net.karolek.store.Store;
import net.karolek.store.columns.StoreColumn;
import net.karolek.store.columns.StoreColumnImpl;
import net.karolek.store.common.PreparedQuery;
import net.karolek.store.common.runner.QueryExecutor;
import net.karolek.store.queries.UpdateQuery;
import net.karolek.store.queries.interfaces.ColumnWhereQuery;
import net.karolek.store.queries.interfaces.QualifedWhereQuery;
import net.karolek.store.queries.modules.LimitModule;
import net.karolek.store.queries.modules.OrderModule;
import net.karolek.store.queries.modules.WhereModule;

import java.util.HashMap;
import java.util.Map;

@Getter
public class UpdateQueryImpl extends StoreQueryImpl<UpdateQuery> implements UpdateQuery {

    private final UpdateQuery instance;

    private Map<StoreColumn, String> sets = new HashMap<>();
    private OrderModule orderModule = new OrderModule();
    private LimitModule limitModule = new LimitModule();
    private WhereModule whereModule = new WhereModule();

    public UpdateQueryImpl() {
        this.instance = this;
    }

    @Override
    public void execute(Store store) {
        if (getTable() == null) throw new IllegalArgumentException();
        StringBuilder sb = new StringBuilder();

        sb.append("UPDATE ").append(getTable().getString()).append(" SET ");

        boolean first = true;
        for (Map.Entry<StoreColumn, String> e : sets.entrySet()) {
            if (!first) sb.append(", ");
            sb.append(e.getKey().getString()).append("='").append(e.getValue()).append("'");
            first = false;
        }

        sb.append(whereModule.getQueryPart());
        sb.append(orderModule.getQueryPart());
        sb.append(limitModule.getQueryPart());
        
        store.runQuery(new PreparedQuery(sb.toString(), callback, now, QueryExecutor.UPDATE));
    }

    @Override
    public UpdateQuery set(StoreColumn column, String value) {
        sets.put(column, value);
        return this;
    }

    @Override
    public UpdateQuery set(String column, String value) {
        sets.put(new StoreColumnImpl(column), value);
        return this;
    }

    @Override
    public UpdateQuery set(String tablePrefix, String column, String value) {
        sets.put(new StoreColumnImpl(column, tablePrefix), value);
        return this;
    }

    @Override
    public UpdateQuery set(StoreColumn column, int value) {
        sets.put(column, String.valueOf(value));
        return this;
    }

    @Override
    public UpdateQuery set(String column, int value) {
        sets.put(new StoreColumnImpl(column), String.valueOf(value));
        return this;
    }

    @Override
    public UpdateQuery set(String tablePrefix, String column, int value) {
        sets.put(new StoreColumnImpl(column, tablePrefix), String.valueOf(value));
        return this;
    }

    @Override
    public UpdateQuery limit(int limit) {
        limitModule.setLimit(limit);
        return this;
    }

    @Override
    public UpdateQuery offset(int offset) {
        return this;
    }

    @Override
    public UpdateQuery order(StoreColumn column, OrderType orderType) {
        orderModule.order(column, orderType);
        return this;
    }

    @Override
    public UpdateQuery order(String column, OrderType orderType) {
        orderModule.order(new StoreColumnImpl(column), orderType);
        return this;
    }

    @Override
    public UpdateQuery order(String tablePrefix, String column, OrderType orderType) {
        orderModule.order(new StoreColumnImpl(column, tablePrefix), orderType);
        return this;
    }

    @Override
    public QualifedWhereQuery<UpdateQuery> where() {
        return new QualifedWhereQuery<UpdateQuery>() {

            @Override
            public ColumnWhereQuery<UpdateQuery> column(String string) {
                whereModule.setColumn(new StoreColumnImpl(string));
                return columnWhereQuery;
            }

            @Override
            public ColumnWhereQuery<UpdateQuery> column(String tablePrefix, String string) {
                whereModule.setColumn(new StoreColumnImpl(string, tablePrefix));
                return columnWhereQuery;
            }

            @Override
            public ColumnWhereQuery<UpdateQuery> column(StoreColumn column) {
                whereModule.setColumn(column);
                return columnWhereQuery;
            }

            @Override
            public UpdateQuery query() {
                whereModule.finalize();
                return getInstance();
            }

            ColumnWhereQuery<UpdateQuery> columnWhereQuery = new ColumnWhereQuery<UpdateQuery>() {
                @Override
                public QualifedWhereQuery<UpdateQuery> equals(String string) {
                    whereModule.equals(string);
                    return where();
                }

                @Override
                public QualifedWhereQuery<UpdateQuery> notEquals(String string) {
                    whereModule.notEquals(string);
                    return where();
                }

                @Override
                public QualifedWhereQuery<UpdateQuery> equals(int value) {
                    whereModule.equals(String.valueOf(value));
                    return where();
                }

                @Override
                public QualifedWhereQuery<UpdateQuery> notEquals(int value) {
                    whereModule.notEquals(String.valueOf(value));
                    return where();
                }

                @Override
                public QualifedWhereQuery<UpdateQuery> like(String string) {
                    whereModule.like(string);
                    return where();
                }

                @Override
                public QualifedWhereQuery<UpdateQuery> notLike(String string) {
                    whereModule.notLike(string);
                    return where();
                }

                @Override
                public QualifedWhereQuery<UpdateQuery> greaterThan(int value) {
                    whereModule.greaterThan(value);
                    return where();
                }

                @Override
                public QualifedWhereQuery<UpdateQuery> greaterOrEqualsThan(int value) {
                    whereModule.greaterOrEqualsThan(value);
                    return where();
                }

                @Override
                public QualifedWhereQuery<UpdateQuery> lessThan(int value) {
                    whereModule.lessThan(value);
                    return where();
                }

                @Override
                public QualifedWhereQuery<UpdateQuery> lessOrEqualsThan(int value) {
                    whereModule.lessOrEqualsThan(value);
                    return where();
                }
            };

        };
    }

}
