package oyster.card.management.support;

import oyster.card.management.models.Journey;
import oyster.card.management.models.Station;
import oyster.card.management.models.TransportType;

public class TripFactory {
    public static Journey newJourney(Station origin, Station destination, TransportType transportType) {
        return Journey.builder()
                .origin(origin)
                .destination(destination)
                .transportType(transportType)
                .build();
    }

    public static Journey newJourney(Station origin, Station destination) {
        return Journey.builder()
                .origin(origin)
                .destination(destination)
                .build();
    }
}
