package net.karolek.store.queries.impl;

import lombok.Getter;
import net.karolek.store.Store;
import net.karolek.store.columns.StoreAllColumnsImpl;
import net.karolek.store.columns.StoreColumn;
import net.karolek.store.columns.StoreColumnImpl;
import net.karolek.store.common.PreparedQuery;
import net.karolek.store.common.runner.QueryExecutor;
import net.karolek.store.condition.Condition;
import net.karolek.store.condition.ConditionBuilder;
import net.karolek.store.queries.SelectQuery;
import net.karolek.store.queries.interfaces.ColumnWhereQuery;
import net.karolek.store.queries.interfaces.QualifedWhereQuery;

import java.util.*;

@Getter
public class SelectQueryImpl extends StoreQueryImpl<SelectQuery> implements SelectQuery {

    private static final StoreColumn ALL_COLUMNS = new StoreAllColumnsImpl();

    private final SelectQuery instance;

    private Set<StoreColumn> columns = new HashSet<>();
    private Map<StoreColumn, OrderType> orders = new HashMap<>();
    private StoreColumn groupColumn;
    private int limit = -1;
    private int offset = -1;
    private String whereCondition = "";
    private Set<Condition> conditions = new LinkedHashSet<>();
    private Condition currentConditon;
    private ConditionBuilder conditionBuilder = new ConditionBuilder();


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
        this.groupColumn = column;
        return this;
    }

    @Override
    public SelectQuery group(String column) {
        this.groupColumn = new StoreColumnImpl(column);
        return this;
    }

    @Override
    public SelectQuery group(String tablePrefix, String column) {
        this.groupColumn = new StoreColumnImpl(column, tablePrefix);
        return this;
    }

    @Override
    public SelectQuery limit(int limit) {
        this.limit = limit;
        return this;
    }

    @Override
    public SelectQuery offset(int offset) {
        this.offset = offset;
        return this;
    }

    @Override
    public SelectQuery order(StoreColumn column, OrderType orderType) {
        this.orders.put(column, orderType);
        return this;
    }

    @Override
    public SelectQuery order(String column, OrderType orderType) {
        this.orders.put(new StoreColumnImpl(column), orderType);
        return this;
    }

    @Override
    public SelectQuery order(String tablePrefix, String column, OrderType orderType) {
        this.orders.put(new StoreColumnImpl(column, tablePrefix), orderType);
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

        if (!whereCondition.equalsIgnoreCase(""))
            sb.append(" WHERE ").append(whereCondition);

        if (groupColumn != null)
            sb.append(" GROUP BY ").append(groupColumn.getString());

        if (orders.size() > 0) {
            sb.append(" ORDER BY ");
            boolean first = true;
            for (Map.Entry<StoreColumn, OrderType> e : orders.entrySet()) {
                if (!first) sb.append(", ");
                sb.append(e.getKey().getString()).append(" ").append(e.getValue().name());
                first = false;
            }
        }

        if (limit > 0) {
            sb.append(" LIMIT ").append(limit);
            if (offset > 0)
                sb.append(", ").append(offset);
        }

        System.out.println(sb.toString());

        store.runQuery(new PreparedQuery(sb.toString(), callback, now, QueryExecutor.SELECT));
    }

    @Override
    public QualifedWhereQuery<SelectQuery> where() {
        return new QualifedWhereQuery<SelectQuery>() {

            @Override
            public ColumnWhereQuery<SelectQuery> column(String string) {
                if (conditionBuilder != null && conditions.size() == 0)
                    conditionBuilder = new ConditionBuilder();

                currentConditon = new Condition();
                currentConditon.setColumn(new StoreColumnImpl(string).getString());
                if (currentConditon != null)
                    conditions.add(currentConditon);

                return columnWhereQuery;
            }

            @Override
            public ColumnWhereQuery<SelectQuery> column(String tablePrefix, String string) {
                if (currentConditon != null)
                    conditions.add(currentConditon);
                currentConditon = new Condition();
                currentConditon.setColumn(new StoreColumnImpl(string, tablePrefix).getString());
                return columnWhereQuery;
            }

            @Override
            public ColumnWhereQuery<SelectQuery> column(StoreColumn column) {
                if (currentConditon != null)
                    conditions.add(currentConditon);
                currentConditon = new Condition();
                currentConditon.setColumn(column.getString());
                return columnWhereQuery;
            }

            @Override
            public SelectQuery query() {
                ConditionBuilder cb = new ConditionBuilder();
                conditions.forEach(cb::comma);
                whereCondition += cb.getString();
                conditions.clear();
                return getInstance();
            }

            ColumnWhereQuery<SelectQuery> columnWhereQuery = new ColumnWhereQuery<SelectQuery>() {
                @Override
                public QualifedWhereQuery<SelectQuery> equals(String string) {
                    if (currentConditon == null) throw new IllegalArgumentException();
                    currentConditon.setValue(string);
                    currentConditon.setConditionType(Condition.ConditionType.EQUALS);
                    return where();
                }

                @Override
                public QualifedWhereQuery<SelectQuery> notEquals(String string) {
                    if (currentConditon == null) throw new IllegalArgumentException();
                    currentConditon.setValue(string);
                    currentConditon.setConditionType(Condition.ConditionType.NOT_EQUALS);
                    return where();
                }

                @Override
                public QualifedWhereQuery<SelectQuery> equals(int value) {
                    if (currentConditon == null) throw new IllegalArgumentException();
                    currentConditon.setValue(String.valueOf(value));
                    currentConditon.setConditionType(Condition.ConditionType.EQUALS);
                    return where();
                }

                @Override
                public QualifedWhereQuery<SelectQuery> notEquals(int value) {
                    if (currentConditon == null) throw new IllegalArgumentException();
                    currentConditon.setValue(String.valueOf(value));
                    currentConditon.setConditionType(Condition.ConditionType.NOT_EQUALS);
                    return where();
                }

                @Override
                public QualifedWhereQuery<SelectQuery> like(String string) {
                    if (currentConditon == null) throw new IllegalArgumentException();
                    currentConditon.setValue(string);
                    currentConditon.setConditionType(Condition.ConditionType.LIKE);
                    return where();
                }

                @Override
                public QualifedWhereQuery<SelectQuery> notLike(String string) {
                    if (currentConditon == null) throw new IllegalArgumentException();
                    currentConditon.setValue(string);
                    currentConditon.setConditionType(Condition.ConditionType.NOT_LIKE);
                    return where();
                }

                @Override
                public QualifedWhereQuery<SelectQuery> greaterThan(int value) {
                    if (currentConditon == null) throw new IllegalArgumentException();
                    currentConditon.setValue(String.valueOf(value));
                    currentConditon.setConditionType(Condition.ConditionType.GREATER_THAN);
                    return where();
                }

                @Override
                public QualifedWhereQuery<SelectQuery> greaterOrEqualsThan(int value) {
                    if (currentConditon == null) throw new IllegalArgumentException();
                    currentConditon.setValue(String.valueOf(value));
                    currentConditon.setConditionType(Condition.ConditionType.GREATER_OR_EQUALS_THAN);
                    return where();
                }

                @Override
                public QualifedWhereQuery<SelectQuery> lessThan(int value) {
                    if (currentConditon == null) throw new IllegalArgumentException();
                    currentConditon.setValue(String.valueOf(value));
                    currentConditon.setConditionType(Condition.ConditionType.LESS_THAN);
                    return where();
                }

                @Override
                public QualifedWhereQuery<SelectQuery> lessOrEqualsThan(int value) {
                    if (currentConditon == null) throw new IllegalArgumentException();
                    currentConditon.setValue(String.valueOf(value));
                    currentConditon.setConditionType(Condition.ConditionType.LESS_OR_EQUALS_THAN);
                    return where();
                }
            };

        };
    }
}
