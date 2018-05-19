package oyster.card.management.model;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class Fare {
    private BigDecimal value;
    private int zonesCrossed;
    private boolean zoneOneCrossed;
    private TransportType transportType;
}
