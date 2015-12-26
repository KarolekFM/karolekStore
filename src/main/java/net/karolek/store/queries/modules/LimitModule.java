package net.karolek.store.queries.modules;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LimitModule implements Module {

    private int limit = -1;
    private int offset = -1;

    @Override
    public String getQueryPart() {
        StringBuilder sb = new StringBuilder();
        if (getLimit() > 0) {
            sb.append(" LIMIT ").append(getLimit());
            if (getOffset() > 0)
                sb.append(" OFFSET ").append(getOffset());
        }
        return sb.toString();
    }
}
