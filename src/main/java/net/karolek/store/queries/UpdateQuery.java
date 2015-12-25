package net.karolek.store.queries;

import net.karolek.store.queries.columns.StoreColumn;

public interface UpdateQuery extends StoreQuery<UpdateQuery>, WhereQuery<UpdateQuery>, LimitQuery<UpdateQuery>, OrderQuery<UpdateQuery> {

    UpdateQuery set(StoreColumn column, String value);

    UpdateQuery set(String column, String value);

    UpdateQuery set(String tablePrefix, String column, String value);

    UpdateQuery set(StoreColumn column, int value);

    UpdateQuery set(String column, int value);

    UpdateQuery set(String tablePrefix, String column, int value);

}
