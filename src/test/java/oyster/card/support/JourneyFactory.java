package oyster.card.support;

import oyster.card.models.Journey;
import oyster.card.models.Station;
import oyster.card.models.TransportType;

import java.util.List;

import static java.util.Arrays.asList;
import static oyster.card.models.TransportType.BUS;
import static oyster.card.models.TransportType.TRAIN;
import static oyster.card.support.StationFactory.chelsea;
import static oyster.card.support.StationFactory.earlsCourt;
import static oyster.card.support.StationFactory.hammersmith;
import static oyster.card.support.StationFactory.holborn;

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

    public static Journey holbornToEarlsCourtByTrain() {
        return  newJourney(holborn(), earlsCourt(), TRAIN);
    }

    public static Journey earlsCourtToChelseaByBus() {
        return newJourney(holborn(), chelsea(), BUS);
    }

    public static Journey earlsCourtToHammersmithByTrain() {
        return newJourney(holborn(), hammersmith(), TRAIN);
    }

    public static List<Journey> journeys() {
        return asList(holbornToEarlsCourtByTrain(), earlsCourtToChelseaByBus(), earlsCourtToHammersmithByTrain());
    }
}
