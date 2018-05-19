package oyster.card.management.commands;

import oyster.card.management.models.Fare;
import oyster.card.management.models.TransportType;
import oyster.card.management.models.Journey;
import oyster.card.management.queries.GetMinimumZonesCrossedQuery;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static java.util.Comparator.comparing;

class CalculateJourneyFareCommand {
    private final List<Fare> fares;
    private final GetMinimumZonesCrossedQuery getMinimumZonesCrossedQuery;

    CalculateJourneyFareCommand(List<Fare> fares, GetMinimumZonesCrossedQuery getMinimumZonesCrossedQuery) {
        this.fares = fares;
        this.getMinimumZonesCrossedQuery = getMinimumZonesCrossedQuery;
    }

    BigDecimal run(Journey journey) {
        int minimumCrossedZones = getMinimumZonesCrossedQuery.run(journey);

        boolean isZoneOneCrossed = (journey.getOrigin().getZones().size() == 1 && journey.getOrigin().getZones().contains(1)) ||
                (journey.getDestination().getZones().size() == 1 && journey.getDestination().getZones().contains(1));

        Optional<Fare> minimumFare = fares.stream().filter(f ->
                (journey.getTransportType() == TransportType.BUS && f.getTransportType() == journey.getTransportType())
                || (
                f.getZonesCrossed() == minimumCrossedZones
                && f.isCanIncludeZoneOne() == isZoneOneCrossed
                && f.getTransportType() == journey.getTransportType()))
                .min(comparing(Fare::getValue));

        return minimumFare.get().getValue();
    }
}
