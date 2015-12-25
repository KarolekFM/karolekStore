package net.karolek.store.tables;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreTableImpl implements StoreTable {

    private final String tableName;
    private String tablePrefix;

    public StoreTableImpl(String tableName) {
        this.tableName = tableName;
    }

    public StoreTableImpl(String tableName, String tablePrefix) {
        this.tableName = tableName;
        this.tablePrefix = tablePrefix;
    }

    @Override
    public String getString() {
        return "`" + getTableName() + "`" + ((getTablePrefix() != null && !getTablePrefix().equalsIgnoreCase("")) ? " `" + getTablePrefix() + "`" : "");
    }
}
