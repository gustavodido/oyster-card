package oyster.card.management.models;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@Value
@Builder
public class Fare {
    private BigDecimal value;
    private int zonesCrossed;

    @Singular
    private List<Integer> excludingZones;

    private TransportType transportType;
}
