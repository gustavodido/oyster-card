package oyster.card.support;

import oyster.card.models.Journey;
import oyster.card.models.Station;
import oyster.card.models.TransportType;

public class JourneyFactory {
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
