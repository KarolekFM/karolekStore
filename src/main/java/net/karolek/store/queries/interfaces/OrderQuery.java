package net.karolek.store.queries.interfaces;

import net.karolek.store.columns.StoreColumn;
import net.karolek.store.queries.StoreQuery;

public interface OrderQuery<T extends StoreQuery> {

    T order(StoreColumn column, OrderType orderType);

    T order(String column, OrderType orderType);

    T order(String tablePrefix, String column, OrderType orderType);

    enum OrderType {
        DESC, ASC;
    }


}
