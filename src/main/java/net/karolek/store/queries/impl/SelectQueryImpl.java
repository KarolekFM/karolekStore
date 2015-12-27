package net.karolek.store.queries.impl;

import lombok.Getter;
import net.karolek.store.Store;
import net.karolek.store.columns.StoreAllColumnsImpl;
import net.karolek.store.columns.StoreColumn;
import net.karolek.store.columns.StoreColumnImpl;
import net.karolek.store.common.PreparedQuery;
import net.karolek.store.common.runner.QueryExecutor;
import net.karolek.store.queries.SelectQuery;
import net.karolek.store.queries.interfaces.ColumnWhereQuery;
import net.karolek.store.queries.interfaces.QualifedWhereQuery;
import net.karolek.store.queries.modules.*;
import net.karolek.store.tables.StoreTable;
import net.karolek.store.tables.StoreTableImpl;

import java.util.HashSet;
import java.util.Set;

@Getter
public class SelectQueryImpl extends StoreQueryImpl<SelectQuery> implements SelectQuery {

    private static final StoreColumn ALL_COLUMNS = new StoreAllColumnsImpl();

    private final SelectQuery instance;

    private Set<StoreColumn> columns = new HashSet<>();
    private JoinModule joinModule = new JoinModule();
    private GroupModule groupModule = new GroupModule();
    private OrderModule orderModule = new OrderModule();
    private LimitModule limitModule = new LimitModule();
    private WhereModule whereModule = new WhereModule();


    public SelectQueryImpl() {
        this.instance = this;
    }

    @Override
    public SelectQuery column(StoreColumn column) {
        this.columns.add(column);
        return this;
    }

    @Override
    public SelectQuery column(String column) {
        this.columns.add(new StoreColumnImpl(column));
        return this;
    }

    @Override
    public SelectQuery column(String tablePrefix, String column) {
        this.columns.add(new StoreColumnImpl(column, tablePrefix));
        return this;
    }

    @Override
    public SelectQuery group(StoreColumn column) {
        groupModule.setGroupColumn(column);
        return this;
    }

    @Override
    public SelectQuery group(String column) {
        groupModule.setGroupColumn(new StoreColumnImpl(column));
        return this;
    }

    @Override
    public SelectQuery group(String tablePrefix, String column) {
        groupModule.setGroupColumn(new StoreColumnImpl(column, tablePrefix));
        return this;
    }

    @Override
    public SelectQuery limit(int limit) {
        limitModule.setLimit(limit);
        return this;
    }

    @Override
    public SelectQuery offset(int offset) {
        limitModule.setOffset(offset);
        return this;
    }

    @Override
    public SelectQuery order(StoreColumn column, OrderType orderType) {
        orderModule.order(column, orderType);
        return this;
    }

    @Override
    public SelectQuery order(String column, OrderType orderType) {
        orderModule.order(new StoreColumnImpl(column), orderType);
        return this;
    }

    @Override
    public SelectQuery order(String tablePrefix, String column, OrderType orderType) {
        orderModule.order(new StoreColumnImpl(column, tablePrefix), orderType);
        return this;
    }

    @Override
    public SelectQuery join(StoreTable table, StoreColumn left, StoreColumn right, JoinType joinType) {
        joinModule.join(table, left, right, joinType);
        return this;
    }

    @Override
    public SelectQuery join(String tablePrefix, String tableName, String leftPrefix, String leftName, String rightPrefix, String rightName, JoinType joinType) {
        joinModule.join(new StoreTableImpl(tableName, tablePrefix), new StoreColumnImpl(leftName, leftPrefix), new StoreColumnImpl(rightName, rightPrefix), joinType);
        return this;
    }

    @Override
    public void execute(Store store) {
        if (getTable() == null) throw new IllegalArgumentException();
        StringBuilder sb = new StringBuilder();

        sb.append("SELECT ");

        if (columns.size() == 0) {
            sb.append(ALL_COLUMNS.getString());
        } else {
            boolean first = true;
            for (StoreColumn column : columns) {
                if (!first) sb.append(", ");
                sb.append(column.getString());
                first = false;
            }
        }

        sb.append(" FROM ").append(getTable().getString());

        sb.append(joinModule.getQueryPart());
        sb.append(whereModule.getQueryPart());
        sb.append(groupModule.getQueryPart());
        sb.append(orderModule.getQueryPart());
        sb.append(limitModule.getQueryPart());

        store.runQuery(new PreparedQuery(sb.toString(), callback, now, QueryExecutor.SELECT));
    }

    @Override
    public QualifedWhereQuery<SelectQuery> where() {
        return new QualifedWhereQuery<SelectQuery>() {

            @Override
            public ColumnWhereQuery<SelectQuery> column(String string) {
                whereModule.setColumn(new StoreColumnImpl(string));
                return columnWhereQuery;
            }

            @Override
            public ColumnWhereQuery<SelectQuery> column(String tablePrefix, String string) {
                whereModule.setColumn(new StoreColumnImpl(string, tablePrefix));
                return columnWhereQuery;
            }

            @Override
            public ColumnWhereQuery<SelectQuery> column(StoreColumn column) {
                whereModule.setColumn(column);
                return columnWhereQuery;
            }

            @Override
            public SelectQuery query() {
                whereModule.finalize();
                return getInstance();
            }

            ColumnWhereQuery<SelectQuery> columnWhereQuery = new ColumnWhereQuery<SelectQuery>() {
                @Override
                public QualifedWhereQuery<SelectQuery> equals(String string) {
                    whereModule.equals(string);
                    return where();
                }

                @Override
                public QualifedWhereQuery<SelectQuery> notEquals(String string) {
                    whereModule.notEquals(string);
                    return where();
                }

                @Override
                public QualifedWhereQuery<SelectQuery> equals(int value) {
                    whereModule.equals(String.valueOf(value));
                    return where();
                }

                @Override
                public QualifedWhereQuery<SelectQuery> notEquals(int value) {
                    whereModule.notEquals(String.valueOf(value));
                    return where();
                }

                @Override
                public QualifedWhereQuery<SelectQuery> like(String string) {
                    whereModule.like(string);
                    return where();
                }

                @Override
                public QualifedWhereQuery<SelectQuery> notLike(String string) {
                    whereModule.notLike(string);
                    return where();
                }

                @Override
                public QualifedWhereQuery<SelectQuery> greaterThan(int value) {
                    whereModule.greaterThan(value);
                    return where();
                }

                @Override
                public QualifedWhereQuery<SelectQuery> greaterOrEqualsThan(int value) {
                    whereModule.greaterOrEqualsThan(value);
                    return where();
                }

                @Override
                public QualifedWhereQuery<SelectQuery> lessThan(int value) {
                    whereModule.lessThan(value);
                    return where();
                }

                @Override
                public QualifedWhereQuery<SelectQuery> lessOrEqualsThan(int value) {
                    whereModule.lessOrEqualsThan(value);
                    return where();
                }
            };

        };
    }
}
