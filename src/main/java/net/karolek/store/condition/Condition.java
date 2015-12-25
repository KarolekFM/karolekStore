package net.karolek.store.condition;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Condition {

    private String column;
    private String value;
    private ConditionType conditionType;

    public Condition() {
    }

    public Condition(String column, String value, ConditionType conditionType) {
        if ((column == null) || (value == null) || (conditionType == null)) {
            throw new IllegalArgumentException();
        }
        this.column = column;
        this.value = value;
        this.conditionType = conditionType;
    }

    @Getter
    public enum ConditionType {
        EQUALS("="), NOT_EQUALS("!="), LIKE("LIKE"), NOT_LIKE("NOT LIKE"), GREATER_THAN(">"), GREATER_OR_EQUALS_THAN(">="), LESS_THAN("<"), LESS_OR_EQUALS_THAN("<=");

        String conditionString;

        ConditionType(String string) {
            this.conditionString = string;
        }
    }

    public String getString() {
        return getColumn() + getConditionType().getConditionString() + "'" + getValue() + "'";
    }


}