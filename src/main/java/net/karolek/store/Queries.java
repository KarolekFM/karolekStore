package net.karolek.store;

import net.karolek.store.queries.DeleteQuery;
import net.karolek.store.queries.InsertQuery;
import net.karolek.store.queries.SelectQuery;
import net.karolek.store.queries.UpdateQuery;
import net.karolek.store.queries.impl.DeleteQueryImpl;
import net.karolek.store.queries.impl.InsertQueryImpl;
import net.karolek.store.queries.impl.SelectQueryImpl;
import net.karolek.store.queries.impl.UpdateQueryImpl;

public class Queries {

    public static SelectQuery select() {
        return new SelectQueryImpl();
    }

    public static UpdateQuery update() {
        return new UpdateQueryImpl();
    }

    public static InsertQuery insert() {
        return new InsertQueryImpl();
    }

    public static DeleteQuery delete() {
        return new DeleteQueryImpl();
    }

}
