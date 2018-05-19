package oyster.card.management.models;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class Fare {
    private BigDecimal value;
    private int zonesCrossed;
    private TransportType transportType;
    private boolean canIncludeZoneOne;
}
