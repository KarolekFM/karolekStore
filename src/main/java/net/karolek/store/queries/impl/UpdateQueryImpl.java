package net.karolek.store.queries.impl;

import lombok.Getter;
import net.karolek.store.Store;
import net.karolek.store.condition.Condition;
import net.karolek.store.condition.ConditionBuilder;
import net.karolek.store.queries.UpdateQuery;
import net.karolek.store.queries.columns.StoreColumn;
import net.karolek.store.queries.columns.StoreColumnImpl;
import net.karolek.store.queries.where.ColumnWhereQuery;
import net.karolek.store.queries.where.QualifedWhereQuery;
import net.karolek.store.runner.PreparedQuery;
import net.karolek.store.runner.QueryExecutor;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

@Getter
public class UpdateQueryImpl extends StoreQueryImpl<UpdateQuery> implements UpdateQuery {

    private final UpdateQuery instance;

    private Map<StoreColumn, String> sets = new HashMap<>();
    private Map<StoreColumn, OrderType> orders = new HashMap<>();
    private int limit = -1;
    private String whereCondition = "";
    private Set<Condition> conditions = new LinkedHashSet<>();
    private Condition currentConditon;
    private ConditionBuilder conditionBuilder = new ConditionBuilder();

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

        if (!whereCondition.equalsIgnoreCase(""))
            sb.append(" WHERE ").append(whereCondition);


        if (orders.size() > 0) {
            sb.append(" ORDER BY ");
            first = true;
            for (Map.Entry<StoreColumn, OrderType> e : orders.entrySet()) {
                if (!first) sb.append(", ");
                sb.append(e.getKey().getString()).append(" ").append(e.getValue().name());
                first = false;
            }
        }

        if (limit > 0)
            sb.append(" LIMIT ").append(limit);


        System.out.println(sb.toString());

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
        this.limit = limit;
        return this;
    }

    @Override
    public UpdateQuery offset(int offset) {
        return this;
    }

    @Override
    public UpdateQuery order(StoreColumn column, OrderType orderType) {
        this.orders.put(column, orderType);
        return this;
    }

    @Override
    public UpdateQuery order(String column, OrderType orderType) {
        this.orders.put(new StoreColumnImpl(column), orderType);
        return this;
    }

    @Override
    public UpdateQuery order(String tablePrefix, String column, OrderType orderType) {
        this.orders.put(new StoreColumnImpl(column, tablePrefix), orderType);
        return this;
    }

    @Override
    public QualifedWhereQuery<UpdateQuery> where() {
        return new QualifedWhereQuery<UpdateQuery>() {

            @Override
            public ColumnWhereQuery<UpdateQuery> column(String string) {
                if (conditionBuilder != null && conditions.size() == 0)
                    conditionBuilder = new ConditionBuilder();

                currentConditon = new Condition();
                currentConditon.setColumn(new StoreColumnImpl(string).getString());
                if (currentConditon != null)
                    conditions.add(currentConditon);

                return columnWhereQuery;
            }

            @Override
            public ColumnWhereQuery<UpdateQuery> column(String tablePrefix, String string) {
                if (currentConditon != null)
                    conditions.add(currentConditon);
                currentConditon = new Condition();
                currentConditon.setColumn(new StoreColumnImpl(string, tablePrefix).getString());
                return columnWhereQuery;
            }

            @Override
            public ColumnWhereQuery<UpdateQuery> column(StoreColumn column) {
                if (currentConditon != null)
                    conditions.add(currentConditon);
                currentConditon = new Condition();
                currentConditon.setColumn(column.getString());
                return columnWhereQuery;
            }

            @Override
            public UpdateQuery query() {
                ConditionBuilder cb = new ConditionBuilder();
                conditions.forEach(cb::comma);
                whereCondition += cb.getString();
                conditions.clear();
                return getInstance();
            }

            ColumnWhereQuery<UpdateQuery> columnWhereQuery = new ColumnWhereQuery<UpdateQuery>() {
                @Override
                public QualifedWhereQuery<UpdateQuery> equals(String string) {
                    if (currentConditon == null) throw new IllegalArgumentException();
                    currentConditon.setValue(string);
                    currentConditon.setConditionType(Condition.ConditionType.EQUALS);
                    return where();
                }

                @Override
                public QualifedWhereQuery<UpdateQuery> notEquals(String string) {
                    if (currentConditon == null) throw new IllegalArgumentException();
                    currentConditon.setValue(string);
                    currentConditon.setConditionType(Condition.ConditionType.NOT_EQUALS);
                    return where();
                }

                @Override
                public QualifedWhereQuery<UpdateQuery> equals(int value) {
                    if (currentConditon == null) throw new IllegalArgumentException();
                    currentConditon.setValue(String.valueOf(value));
                    currentConditon.setConditionType(Condition.ConditionType.EQUALS);
                    return where();
                }

                @Override
                public QualifedWhereQuery<UpdateQuery> notEquals(int value) {
                    if (currentConditon == null) throw new IllegalArgumentException();
                    currentConditon.setValue(String.valueOf(value));
                    currentConditon.setConditionType(Condition.ConditionType.NOT_EQUALS);
                    return where();
                }

                @Override
                public QualifedWhereQuery<UpdateQuery> like(String string) {
                    if (currentConditon == null) throw new IllegalArgumentException();
                    currentConditon.setValue(string);
                    currentConditon.setConditionType(Condition.ConditionType.LIKE);
                    return where();
                }

                @Override
                public QualifedWhereQuery<UpdateQuery> notLike(String string) {
                    if (currentConditon == null) throw new IllegalArgumentException();
                    currentConditon.setValue(string);
                    currentConditon.setConditionType(Condition.ConditionType.NOT_LIKE);
                    return where();
                }

                @Override
                public QualifedWhereQuery<UpdateQuery> greaterThan(int value) {
                    if (currentConditon == null) throw new IllegalArgumentException();
                    currentConditon.setValue(String.valueOf(value));
                    currentConditon.setConditionType(Condition.ConditionType.GREATER_THAN);
                    return where();
                }

                @Override
                public QualifedWhereQuery<UpdateQuery> greaterOrEqualsThan(int value) {
                    if (currentConditon == null) throw new IllegalArgumentException();
                    currentConditon.setValue(String.valueOf(value));
                    currentConditon.setConditionType(Condition.ConditionType.GREATER_OR_EQUALS_THAN);
                    return where();
                }

                @Override
                public QualifedWhereQuery<UpdateQuery> lessThan(int value) {
                    if (currentConditon == null) throw new IllegalArgumentException();
                    currentConditon.setValue(String.valueOf(value));
                    currentConditon.setConditionType(Condition.ConditionType.LESS_THAN);
                    return where();
                }

                @Override
                public QualifedWhereQuery<UpdateQuery> lessOrEqualsThan(int value) {
                    if (currentConditon == null) throw new IllegalArgumentException();
                    currentConditon.setValue(String.valueOf(value));
                    currentConditon.setConditionType(Condition.ConditionType.LESS_OR_EQUALS_THAN);
                    return where();
                }
            };

        };
    }

}
