package net.karolek.store.queries;

public interface CustomQuery extends StoreQuery<CustomQuery> {

    CustomQuery query(String query);

}
