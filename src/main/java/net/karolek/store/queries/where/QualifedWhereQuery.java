package net.karolek.store.queries.where;

import net.karolek.store.queries.StoreQuery;
import net.karolek.store.queries.columns.StoreColumn;

public interface QualifedWhereQuery<T extends StoreQuery> {

    ColumnWhereQuery<T> column(String string);

    ColumnWhereQuery<T> column(String tablePrefix, String string);

    ColumnWhereQuery<T> column(StoreColumn column);

    T query();

}
