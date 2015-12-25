package net.karolek.store.queries.where;

import net.karolek.store.queries.StoreQuery;

public interface ColumnWhereQuery<T extends StoreQuery> {

    QualifedWhereQuery<T> equals(String string);

    QualifedWhereQuery<T> notEquals(String string);

    QualifedWhereQuery<T> equals(int value);

    QualifedWhereQuery<T> notEquals(int value);

    QualifedWhereQuery<T> like(String string);

    QualifedWhereQuery<T> notLike(String string);

    QualifedWhereQuery<T> greaterThan(int value);

    QualifedWhereQuery<T> greaterOrEqualsThan(int value);

    QualifedWhereQuery<T> lessThan(int value);

    QualifedWhereQuery<T> lessOrEqualsThan(int value);

}
