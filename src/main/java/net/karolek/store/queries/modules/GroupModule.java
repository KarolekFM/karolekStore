package net.karolek.store.queries.modules;

import lombok.Getter;
import lombok.Setter;
import net.karolek.store.columns.StoreColumn;

@Getter
@Setter
public class GroupModule implements Module {

    private StoreColumn groupColumn;

    @Override
    public String getQueryPart() {
        StringBuilder sb = new StringBuilder();
        if (groupColumn != null)
            sb.append(" GROUP BY ").append(groupColumn.getString());
        return sb.toString();
    }
}
