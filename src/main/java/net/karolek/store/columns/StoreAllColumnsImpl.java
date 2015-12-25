package net.karolek.store.columns;

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
