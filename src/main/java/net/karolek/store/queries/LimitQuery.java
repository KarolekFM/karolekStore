package net.karolek.store.queries;

public interface LimitQuery<T extends StoreQuery> {

    T limit(int limit);

    T offset(int offset);

}
