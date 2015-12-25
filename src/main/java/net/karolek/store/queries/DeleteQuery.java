package net.karolek.store.queries;

import net.karolek.store.queries.interfaces.LimitQuery;
import net.karolek.store.queries.interfaces.OrderQuery;
import net.karolek.store.queries.interfaces.WhereQuery;

public interface DeleteQuery extends StoreQuery<DeleteQuery>, WhereQuery<DeleteQuery>, LimitQuery<DeleteQuery>, OrderQuery<DeleteQuery> {
}
