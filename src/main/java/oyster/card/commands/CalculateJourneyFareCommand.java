package oyster.card.commands;

import oyster.card.models.Fare;
import oyster.card.models.Journey;
import oyster.card.queries.GetMinimumZonesCrossedQuery;
import oyster.card.queries.IsZoneOneCrossedQuery;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static java.util.Comparator.comparing;
import static oyster.card.models.TransportType.BUS;

class CalculateJourneyFareCommand {
    private final List<Fare> fares;
    private final GetMinimumZonesCrossedQuery getMinimumZonesCrossedQuery;
    private final IsZoneOneCrossedQuery isZoneOneCrossedQuery;

    CalculateJourneyFareCommand(List<Fare> fares, GetMinimumZonesCrossedQuery getMinimumZonesCrossedQuery, IsZoneOneCrossedQuery isZoneOneCrossedQuery) {
        this.fares = fares;
        this.getMinimumZonesCrossedQuery = getMinimumZonesCrossedQuery;
        this.isZoneOneCrossedQuery = isZoneOneCrossedQuery;
    }

    BigDecimal run(Journey journey) {
        int minimumCrossedZones = getMinimumZonesCrossedQuery.run(journey);
        boolean isZoneOneCrossed = isZoneOneCrossedQuery.run(journey);

        Predicate<Fare> busTransport = f ->  journey.getTransportType() == BUS && f.getTransportType() == BUS;
        Predicate<Fare> zonesCrossed = f -> f.getZonesCrossed() == minimumCrossedZones;
        Predicate<Fare> isZoneOneAllowed = f -> f.isCanIncludeZoneOne() == isZoneOneCrossed;
        Predicate<Fare> transportType = f -> f.getTransportType() == journey.getTransportType();

        Predicate<Fare> faresFilter =
                busTransport.or(zonesCrossed.and(isZoneOneAllowed).and(transportType));

        Optional<Fare> minimumFare = fares.stream()
                .filter(faresFilter)
                .min(comparing(Fare::getValue));

        return minimumFare.get().getValue();
    }
}
