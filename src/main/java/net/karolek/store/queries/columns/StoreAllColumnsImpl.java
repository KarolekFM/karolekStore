package net.karolek.store.queries.columns;

public class StoreAllColumnsImpl implements StoreColumn {
    @Override
    public String getColumnName() {
        return "*";
    }

    @Override
    public String getString() {
        return "*";
    }
}
