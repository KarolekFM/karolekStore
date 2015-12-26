package net.karolek.store.queries.impl;

import lombok.Getter;
import net.karolek.store.Store;
import net.karolek.store.columns.StoreColumn;
import net.karolek.store.columns.StoreColumnImpl;
import net.karolek.store.common.PreparedQuery;
import net.karolek.store.common.runner.QueryExecutor;
import net.karolek.store.queries.DeleteQuery;
import net.karolek.store.queries.interfaces.ColumnWhereQuery;
import net.karolek.store.queries.interfaces.QualifedWhereQuery;
import net.karolek.store.queries.modules.LimitModule;
import net.karolek.store.queries.modules.OrderModule;
import net.karolek.store.queries.modules.WhereModule;

@Getter
public class DeleteQueryImpl extends StoreQueryImpl<DeleteQuery> implements DeleteQuery {

    private final DeleteQuery instance;

    private OrderModule orderModule = new OrderModule();
    private LimitModule limitModule = new LimitModule();
    private WhereModule whereModule = new WhereModule();

    public DeleteQueryImpl() {
        this.instance = this;
    }

    @Override
    public DeleteQuery limit(int limit) {
        limitModule.setLimit(limit);
        return this;
    }

    @Override
    public DeleteQuery offset(int offset) {
        return this;
    }

    @Override
    public DeleteQuery order(StoreColumn column, OrderType orderType) {
        orderModule.order(column, orderType);
        return this;
    }

    @Override
    public DeleteQuery order(String column, OrderType orderType) {
        orderModule.order(new StoreColumnImpl(column), orderType);
        return this;
    }

    @Override
    public DeleteQuery order(String tablePrefix, String column, OrderType orderType) {
        orderModule.order(new StoreColumnImpl(column, tablePrefix), orderType);
        return this;
    }

    @Override
    public void execute(Store store) {
        if (getTable() == null) throw new IllegalArgumentException();
        StringBuilder sb = new StringBuilder();

        sb.append("DELETE FROM ").append(getTable().getString());

        sb.append(whereModule.getQueryPart());
        sb.append(orderModule.getQueryPart());
        sb.append(limitModule.getQueryPart());

        store.runQuery(new PreparedQuery(sb.toString(), callback, now, QueryExecutor.UPDATE));
    }

    @Override
    public QualifedWhereQuery<DeleteQuery> where() {
        return new QualifedWhereQuery<DeleteQuery>() {

            @Override
            public ColumnWhereQuery<DeleteQuery> column(String string) {
                whereModule.setColumn(new StoreColumnImpl(string));
                return columnWhereQuery;
            }

            @Override
            public ColumnWhereQuery<DeleteQuery> column(String tablePrefix, String string) {
                whereModule.setColumn(new StoreColumnImpl(string, tablePrefix));
                return columnWhereQuery;
            }

            @Override
            public ColumnWhereQuery<DeleteQuery> column(StoreColumn column) {
                whereModule.setColumn(column);
                return columnWhereQuery;
            }

            @Override
            public DeleteQuery query() {
                whereModule.finalize();
                return getInstance();
            }

            ColumnWhereQuery<DeleteQuery> columnWhereQuery = new ColumnWhereQuery<DeleteQuery>() {
                @Override
                public QualifedWhereQuery<DeleteQuery> equals(String string) {
                    whereModule.equals(string);
                    return where();
                }

                @Override
                public QualifedWhereQuery<DeleteQuery> notEquals(String string) {
                    whereModule.notEquals(string);
                    return where();
                }

                @Override
                public QualifedWhereQuery<DeleteQuery> equals(int value) {
                    whereModule.equals(String.valueOf(value));
                    return where();
                }

                @Override
                public QualifedWhereQuery<DeleteQuery> notEquals(int value) {
                    whereModule.notEquals(String.valueOf(value));
                    return where();
                }

                @Override
                public QualifedWhereQuery<DeleteQuery> like(String string) {
                    whereModule.like(string);
                    return where();
                }

                @Override
                public QualifedWhereQuery<DeleteQuery> notLike(String string) {
                    whereModule.notLike(string);
                    return where();
                }

                @Override
                public QualifedWhereQuery<DeleteQuery> greaterThan(int value) {
                    whereModule.greaterThan(value);
                    return where();
                }

                @Override
                public QualifedWhereQuery<DeleteQuery> greaterOrEqualsThan(int value) {
                    whereModule.greaterOrEqualsThan(value);
                    return where();
                }

                @Override
                public QualifedWhereQuery<DeleteQuery> lessThan(int value) {
                    whereModule.lessThan(value);
                    return where();
                }

                @Override
                public QualifedWhereQuery<DeleteQuery> lessOrEqualsThan(int value) {
                    whereModule.lessOrEqualsThan(value);
                    return where();
                }
            };

        };
    }
}
