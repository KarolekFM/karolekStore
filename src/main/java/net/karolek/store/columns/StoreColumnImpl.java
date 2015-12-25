package net.karolek.store.columns;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreColumnImpl implements StoreColumn {

    private final String columnName;
    private String tablePrefix;

    public StoreColumnImpl(String columnName, String tablePrefix) {
        this.columnName = columnName;
        this.tablePrefix = tablePrefix;
    }

    public StoreColumnImpl(String columnName) {
        this.columnName = columnName;
    }

    @Override
    public String getString() {
        return ((getTablePrefix() != null && !getTablePrefix().equalsIgnoreCase("")) ? "`" + getTablePrefix() + "`." : "") + "`" + getColumnName() + "`";
    }
}
