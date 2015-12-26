package net.karolek.store.queries.modules;

import lombok.Getter;
import lombok.Setter;
import net.karolek.store.columns.StoreColumn;
import net.karolek.store.condition.Condition;
import net.karolek.store.condition.ConditionBuilder;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
public class WhereModule implements Module {

    private String whereCondition = "";
    private Set<Condition> conditions = new LinkedHashSet<>();
    private Condition currentConditon;
    private ConditionBuilder conditionBuilder = new ConditionBuilder();

    public void setColumn(StoreColumn column) {
        if (conditionBuilder != null && conditions.size() == 0)
            conditionBuilder = new ConditionBuilder();
        currentConditon = new Condition();
        currentConditon.setColumn(column.getString());
        if (currentConditon != null)
            conditions.add(currentConditon);
    }

    public void finalize() {
        ConditionBuilder cb = new ConditionBuilder();
        conditions.forEach(cb::comma);
        whereCondition += cb.getString();
        conditions.clear();
    }

    public void equals(String string) {
        if (currentConditon == null) throw new IllegalArgumentException();
        currentConditon.setValue(string);
        currentConditon.setConditionType(Condition.ConditionType.EQUALS);
    }

    public void notEquals(String string) {
        if (currentConditon == null) throw new IllegalArgumentException();
        currentConditon.setValue(string);
        currentConditon.setConditionType(Condition.ConditionType.NOT_EQUALS);
    }

    public void like(String string) {
        if (currentConditon == null) throw new IllegalArgumentException();
        currentConditon.setValue(string);
        currentConditon.setConditionType(Condition.ConditionType.LIKE);
    }

    public void notLike(String string) {
        if (currentConditon == null) throw new IllegalArgumentException();
        currentConditon.setValue(string);
        currentConditon.setConditionType(Condition.ConditionType.NOT_LIKE);
    }

    public void greaterThan(int value) {
        if (currentConditon == null) throw new IllegalArgumentException();
        currentConditon.setValue(String.valueOf(value));
        currentConditon.setConditionType(Condition.ConditionType.GREATER_THAN);
    }

    public void greaterOrEqualsThan(int value) {
        if (currentConditon == null) throw new IllegalArgumentException();
        currentConditon.setValue(String.valueOf(value));
        currentConditon.setConditionType(Condition.ConditionType.GREATER_OR_EQUALS_THAN);
    }

    public void lessThan(int value) {
        if (currentConditon == null) throw new IllegalArgumentException();
        currentConditon.setValue(String.valueOf(value));
        currentConditon.setConditionType(Condition.ConditionType.LESS_THAN);
    }

    public void lessOrEqualsThan(int value) {
        if (currentConditon == null) throw new IllegalArgumentException();
        currentConditon.setValue(String.valueOf(value));
        currentConditon.setConditionType(Condition.ConditionType.LESS_OR_EQUALS_THAN);
    }

    @Override
    public String getQueryPart() {
        finalize();
        StringBuilder sb = new StringBuilder();
        if (!whereCondition.equalsIgnoreCase(""))
            sb.append(" WHERE ").append(whereCondition);
        return sb.toString();
    }
}
