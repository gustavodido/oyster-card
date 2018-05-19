package oyster.card.management.models;

import lombok.Value;

@Value
public class Trip {
    private Station origin;
    private Station destination;
    private TransportType transportType;
}
