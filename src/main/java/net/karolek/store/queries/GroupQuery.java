package net.karolek.store.queries;

import net.karolek.store.queries.columns.StoreColumn;

public interface GroupQuery<T extends StoreQuery> {

    T group(StoreColumn column);

    T group(String column);

    T group(String tablePrefix, String column);

}
