package net.karolek.store.queries;

import net.karolek.store.columns.StoreColumn;
import net.karolek.store.queries.interfaces.*;

public interface SelectQuery extends StoreQuery<SelectQuery>, WhereQuery<SelectQuery>, GroupQuery<SelectQuery>, LimitQuery<SelectQuery>, OrderQuery<SelectQuery>, JoinQuery<SelectQuery> {

    SelectQuery column(StoreColumn column);

    SelectQuery column(String column);

    SelectQuery column(String tablePrefix, String column);

}
