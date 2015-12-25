package net.karolek.store.queries.interfaces;

import net.karolek.store.queries.StoreQuery;

public interface WhereQuery<T extends StoreQuery> {

    QualifedWhereQuery<T> where();

}
