package net.karolek.store.queries.impl;

import lombok.Getter;
import net.karolek.store.Store;
import net.karolek.store.columns.StoreColumn;
import net.karolek.store.columns.StoreColumnImpl;
import net.karolek.store.common.PreparedQuery;
import net.karolek.store.common.runner.QueryExecutor;
import net.karolek.store.condition.Condition;
import net.karolek.store.condition.ConditionBuilder;
import net.karolek.store.queries.DeleteQuery;
import net.karolek.store.queries.interfaces.ColumnWhereQuery;
import net.karolek.store.queries.interfaces.QualifedWhereQuery;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

@Getter
public class DeleteQueryImpl extends StoreQueryImpl<DeleteQuery> implements DeleteQuery {

    private final DeleteQuery instance;

    private Map<StoreColumn, OrderType> orders = new HashMap<>();
    private int limit = -1;
    private String whereCondition = "";
    private Set<Condition> conditions = new LinkedHashSet<>();
    private Condition currentConditon;
    private ConditionBuilder conditionBuilder = new ConditionBuilder();

    public DeleteQueryImpl() {
        this.instance = this;
    }

    @Override
    public DeleteQuery limit(int limit) {
        this.limit = limit;
        return this;
    }

    @Override
    public DeleteQuery offset(int offset) {
        return this;
    }

    @Override
    public DeleteQuery order(StoreColumn column, OrderType orderType) {
        this.orders.put(column, orderType);
        return this;
    }

    @Override
    public DeleteQuery order(String column, OrderType orderType) {
        this.orders.put(new StoreColumnImpl(column), orderType);
        return this;
    }

    @Override
    public DeleteQuery order(String tablePrefix, String column, OrderType orderType) {
        this.orders.put(new StoreColumnImpl(column, tablePrefix), orderType);
        return this;
    }

    @Override
    public void execute(Store store) {
        if (getTable() == null) throw new IllegalArgumentException();
        StringBuilder sb = new StringBuilder();

        sb.append("DELETE FROM ").append(getTable().getString());

        if (!whereCondition.equalsIgnoreCase(""))
            sb.append(" WHERE ").append(whereCondition);

        if (orders.size() > 0) {
            sb.append(" ORDER BY ");
            boolean first = true;
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
    public QualifedWhereQuery<DeleteQuery> where() {
        return new QualifedWhereQuery<DeleteQuery>() {

            @Override
            public ColumnWhereQuery<DeleteQuery> column(String string) {
                if (conditionBuilder != null && conditions.size() == 0)
                    conditionBuilder = new ConditionBuilder();

                currentConditon = new Condition();
                currentConditon.setColumn(new StoreColumnImpl(string).getString());
                if (currentConditon != null)
                    conditions.add(currentConditon);

                return columnWhereQuery;
            }

            @Override
            public ColumnWhereQuery<DeleteQuery> column(String tablePrefix, String string) {
                if (currentConditon != null)
                    conditions.add(currentConditon);
                currentConditon = new Condition();
                currentConditon.setColumn(new StoreColumnImpl(string, tablePrefix).getString());
                return columnWhereQuery;
            }

            @Override
            public ColumnWhereQuery<DeleteQuery> column(StoreColumn column) {
                if (currentConditon != null)
                    conditions.add(currentConditon);
                currentConditon = new Condition();
                currentConditon.setColumn(column.getString());
                return columnWhereQuery;
            }

            @Override
            public DeleteQuery query() {
                ConditionBuilder cb = new ConditionBuilder();
                conditions.forEach(cb::comma);
                whereCondition += cb.getString();
                conditions.clear();
                return getInstance();
            }

            ColumnWhereQuery<DeleteQuery> columnWhereQuery = new ColumnWhereQuery<DeleteQuery>() {
                @Override
                public QualifedWhereQuery<DeleteQuery> equals(String string) {
                    if (currentConditon == null) throw new IllegalArgumentException();
                    currentConditon.setValue(string);
                    currentConditon.setConditionType(Condition.ConditionType.EQUALS);
                    return where();
                }

                @Override
                public QualifedWhereQuery<DeleteQuery> notEquals(String string) {
                    if (currentConditon == null) throw new IllegalArgumentException();
                    currentConditon.setValue(string);
                    currentConditon.setConditionType(Condition.ConditionType.NOT_EQUALS);
                    return where();
                }

                @Override
                public QualifedWhereQuery<DeleteQuery> equals(int value) {
                    if (currentConditon == null) throw new IllegalArgumentException();
                    currentConditon.setValue(String.valueOf(value));
                    currentConditon.setConditionType(Condition.ConditionType.EQUALS);
                    return where();
                }

                @Override
                public QualifedWhereQuery<DeleteQuery> notEquals(int value) {
                    if (currentConditon == null) throw new IllegalArgumentException();
                    currentConditon.setValue(String.valueOf(value));
                    currentConditon.setConditionType(Condition.ConditionType.NOT_EQUALS);
                    return where();
                }

                @Override
                public QualifedWhereQuery<DeleteQuery> like(String string) {
                    if (currentConditon == null) throw new IllegalArgumentException();
                    currentConditon.setValue(string);
                    currentConditon.setConditionType(Condition.ConditionType.LIKE);
                    return where();
                }

                @Override
                public QualifedWhereQuery<DeleteQuery> notLike(String string) {
                    if (currentConditon == null) throw new IllegalArgumentException();
                    currentConditon.setValue(string);
                    currentConditon.setConditionType(Condition.ConditionType.NOT_LIKE);
                    return where();
                }

                @Override
                public QualifedWhereQuery<DeleteQuery> greaterThan(int value) {
                    if (currentConditon == null) throw new IllegalArgumentException();
                    currentConditon.setValue(String.valueOf(value));
                    currentConditon.setConditionType(Condition.ConditionType.GREATER_THAN);
                    return where();
                }

                @Override
                public QualifedWhereQuery<DeleteQuery> greaterOrEqualsThan(int value) {
                    if (currentConditon == null) throw new IllegalArgumentException();
                    currentConditon.setValue(String.valueOf(value));
                    currentConditon.setConditionType(Condition.ConditionType.GREATER_OR_EQUALS_THAN);
                    return where();
                }

                @Override
                public QualifedWhereQuery<DeleteQuery> lessThan(int value) {
                    if (currentConditon == null) throw new IllegalArgumentException();
                    currentConditon.setValue(String.valueOf(value));
                    currentConditon.setConditionType(Condition.ConditionType.LESS_THAN);
                    return where();
                }

                @Override
                public QualifedWhereQuery<DeleteQuery> lessOrEqualsThan(int value) {
                    if (currentConditon == null) throw new IllegalArgumentException();
                    currentConditon.setValue(String.valueOf(value));
                    currentConditon.setConditionType(Condition.ConditionType.LESS_OR_EQUALS_THAN);
                    return where();
                }
            };

        };
    }
}
