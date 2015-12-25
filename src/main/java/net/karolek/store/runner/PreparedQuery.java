package net.karolek.store.runner;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PreparedQuery {

    private final String query;
    private final QueryCallback callback;
    private final boolean now;
    private final QueryExecutor queryExecutor;

    public PreparedQuery(String query, QueryCallback callback, boolean now, QueryExecutor queryExecutor) {
        this.query = query;
        this.callback = callback;
        this.now = now;
        this.queryExecutor = queryExecutor;
    }
}
