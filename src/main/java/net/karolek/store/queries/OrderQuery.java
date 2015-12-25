package net.karolek.store.queries;

import net.karolek.store.queries.columns.StoreColumn;

public interface OrderQuery<T extends StoreQuery> {

    T order(StoreColumn column, OrderType orderType);

    T order(String column, OrderType orderType);

    T order(String tablePrefix, String column, OrderType orderType);

    enum OrderType {
        DESC, ASC;
    }


}
