package net.karolek.store.queries;

import net.karolek.store.queries.columns.StoreColumn;

public interface SelectQuery extends StoreQuery<SelectQuery>, WhereQuery<SelectQuery>, GroupQuery<SelectQuery>, LimitQuery<SelectQuery>, OrderQuery<SelectQuery> {

    SelectQuery column(StoreColumn column);

    SelectQuery column(String column);

    SelectQuery column(String tablePrefix, String column);

}
