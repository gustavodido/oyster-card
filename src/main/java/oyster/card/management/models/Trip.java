package oyster.card.management.models;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Trip {
    private Station origin;
    private Station destination;
    private TransportType transportType;
}
