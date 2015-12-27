package net.karolek.store.queries.interfaces;

import lombok.Getter;
import net.karolek.store.columns.StoreColumn;
import net.karolek.store.queries.StoreQuery;
import net.karolek.store.tables.StoreTable;

public interface JoinQuery<T extends StoreQuery> {

    enum JoinType {

        JOIN("JOIN"), LEFT_JOIN("LEFT JOIN"), RIGHT_JOIN("RIGHT JOIN"), INNER_JOIN("INNER JOIN"), LEFT_OUTER_JOIN("LEFT OUTER JOIN"), RIGHT_OUTER_JOIN("RIGHT OUTER JOIN");

        @Getter
        private String name;

        JoinType(String name) {
            this.name = name;
        }

    }

    T join(StoreTable table, StoreColumn left, StoreColumn right, JoinType joinType);

    T join(String tablePrefix, String tableName, String leftPrefix, String leftName, String rightPrefix, String rightName, JoinType joinType);

}
