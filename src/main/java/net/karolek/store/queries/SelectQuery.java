package net.karolek.store.queries;

import net.karolek.store.columns.StoreColumn;
import net.karolek.store.queries.interfaces.GroupQuery;
import net.karolek.store.queries.interfaces.LimitQuery;
import net.karolek.store.queries.interfaces.OrderQuery;
import net.karolek.store.queries.interfaces.WhereQuery;

public interface SelectQuery extends StoreQuery<SelectQuery>, WhereQuery<SelectQuery>, GroupQuery<SelectQuery>, LimitQuery<SelectQuery>, OrderQuery<SelectQuery> {

    SelectQuery column(StoreColumn column);

    SelectQuery column(String column);

    SelectQuery column(String tablePrefix, String column);

}
