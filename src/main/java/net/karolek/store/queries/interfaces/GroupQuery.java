package net.karolek.store.queries.interfaces;

import net.karolek.store.columns.StoreColumn;
import net.karolek.store.queries.StoreQuery;

public interface GroupQuery<T extends StoreQuery> {

    T group(StoreColumn column);

    T group(String column);

    T group(String tablePrefix, String column);

}
