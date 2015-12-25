package net.karolek.store.queries.interfaces;

import net.karolek.store.queries.StoreQuery;

public interface LimitQuery<T extends StoreQuery> {

    T limit(int limit);

    T offset(int offset);

}
