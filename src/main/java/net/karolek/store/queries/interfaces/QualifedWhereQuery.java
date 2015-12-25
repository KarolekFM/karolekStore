package net.karolek.store.queries.interfaces;

import net.karolek.store.columns.StoreColumn;
import net.karolek.store.queries.StoreQuery;

public interface QualifedWhereQuery<T extends StoreQuery> {

    ColumnWhereQuery<T> column(String string);

    ColumnWhereQuery<T> column(String tablePrefix, String string);

    ColumnWhereQuery<T> column(StoreColumn column);

    T query();

}
