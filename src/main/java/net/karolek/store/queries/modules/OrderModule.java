package net.karolek.store.queries.modules;

import lombok.Getter;
import lombok.Setter;
import net.karolek.store.columns.StoreColumn;
import net.karolek.store.queries.interfaces.OrderQuery;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class OrderModule implements Module {

    private final Map<StoreColumn, OrderQuery.OrderType> orders = new HashMap<>();

    public void order(StoreColumn column, OrderQuery.OrderType orderType) {
        orders.put(column, orderType);
    }

    @Override
    public String getQueryPart() {
        StringBuilder sb = new StringBuilder();
        if (orders.size() > 0) {
            sb.append(" ORDER BY ");
            boolean first = true;
            for (Map.Entry<StoreColumn, OrderQuery.OrderType> e : orders.entrySet()) {
                if (!first) sb.append(", ");
                sb.append(e.getKey().getString()).append(" ").append(e.getValue().name());
                first = false;
            }
        }
        return sb.toString();
    }
}
