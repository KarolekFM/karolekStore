package net.karolek.store;

import net.karolek.store.queries.*;
import net.karolek.store.queries.impl.*;

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

    public static CustomQuery customQuery() {
        return new CustomQueryImpl();
    }

}
