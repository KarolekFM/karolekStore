package net.karolek.store.queries;

import net.karolek.store.queries.where.QualifedWhereQuery;

public interface WhereQuery<T extends StoreQuery> {

    QualifedWhereQuery<T> where();

}
