package oyster.card.queries;

import oyster.card.models.Journey;
import oyster.card.models.Station;

import java.util.function.Predicate;

public class IsZoneOneCrossedQuery {
    public boolean run(Journey journey) {
        Predicate<Station> isStationInZoneOne = s -> s.getZones().size() == 1 && s.getZones().contains(1);

        return isStationInZoneOne.test(journey.getOrigin())
                || isStationInZoneOne.test(journey.getDestination());
    }
}
