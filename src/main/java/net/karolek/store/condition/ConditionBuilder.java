package net.karolek.store.condition;

import lombok.Getter;

public class ConditionBuilder {

    @Getter
    private String string = "";
    private boolean first = true;

    public ConditionBuilder() {
    }

    public ConditionBuilder(Condition condition) {
        this.string = condition.getString();
    }


    public ConditionBuilder comma(Condition condition) {
        if (!first) {
            this.string += ", " + condition.getString();
        } else {
            this.string += condition.getString();
        }
        first = false;
        return this;
    }

    public ConditionBuilder or(Condition condition) {
        this.string = (this.string + " OR " + condition.getString());
        return this;
    }

    public ConditionBuilder or(ConditionBuilder conditionBuilder) {
        this.string = ("(" + this.string + ") OR (" + conditionBuilder.getString() + ")");
        return this;
    }

    public ConditionBuilder and(Condition condition) {
        this.string = (this.string + " AND " + condition.getString());
        return this;
    }

    public ConditionBuilder and(ConditionBuilder conditionBuilder) {
        this.string = ("(" + this.string + ") AND (" + conditionBuilder.getString() + ")");
        return this;
    }

    public static Condition like(String column, String value) {
        return new Condition(column, value, Condition.ConditionType.LIKE);
    }

    public static Condition notLike(String column, String value) {
        return new Condition(column, value, Condition.ConditionType.NOT_LIKE);
    }

    public static Condition equals(String column, String value) {
        return new Condition(column, value, Condition.ConditionType.EQUALS);
    }

    public static Condition notEquals(String column, String value) {
        return new Condition(column, value, Condition.ConditionType.NOT_EQUALS);
    }

    public static Condition equals(String column, int value) {
        return new Condition(column, String.valueOf(value), Condition.ConditionType.EQUALS);
    }

    public static Condition notEquals(String column, int value) {
        return new Condition(column, String.valueOf(value), Condition.ConditionType.NOT_EQUALS);
    }

    public static Condition greaterThan(String column, int value) {
        return new Condition(column, String.valueOf(value), Condition.ConditionType.GREATER_THAN);
    }

    public static Condition greaterOrEqualsThan(String column, int value) {
        return new Condition(column, String.valueOf(value), Condition.ConditionType.GREATER_OR_EQUALS_THAN);
    }

    public static Condition lessThan(String column, int value) {
        return new Condition(column, String.valueOf(value), Condition.ConditionType.LESS_THAN);
    }

    public static Condition lessOrEqualsThan(String column, int value) {
        return new Condition(column, String.valueOf(value), Condition.ConditionType.LESS_OR_EQUALS_THAN);
    }
}