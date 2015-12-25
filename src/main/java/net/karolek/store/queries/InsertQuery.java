package net.karolek.store.queries;

import net.karolek.store.columns.StoreColumn;

public interface InsertQuery extends StoreQuery<InsertQuery> {

    InsertQuery add(StoreColumn column, String value);

    InsertQuery add(String column, String value);

    InsertQuery add(String tablePrefix, String column, String value);

    InsertQuery add(StoreColumn column, int value);

    InsertQuery add(String column, int value);

    InsertQuery add(String tablePrefix, String column, int value);

}
