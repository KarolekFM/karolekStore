package net.karolek.store.queries.modules;

import lombok.Getter;
import lombok.Setter;
import net.karolek.store.columns.StoreColumn;
import net.karolek.store.queries.interfaces.JoinQuery;
import net.karolek.store.tables.StoreTable;

@Getter
@Setter
public class JoinModule implements Module {

    private StoreTable table;
    private StoreColumn left;
    private StoreColumn right;
    private JoinQuery.JoinType joinType;

    public void join(StoreTable table, StoreColumn left, StoreColumn right, JoinQuery.JoinType joinType) {
        this.table = table;
        this.left = left;
        this.right = right;
        this.joinType = joinType;
    }

    @Override
    public String getQueryPart() {
        StringBuilder sb = new StringBuilder();
        if (getTable() != null && getLeft() != null && getRight() != null && getJoinType() != null) {
            sb.append(joinType.getName()).append(" ").append(table.getString()).append(" ON ").append(left.getString()).append("=").append(right.getString());
        }
        return sb.toString();
    }
}
