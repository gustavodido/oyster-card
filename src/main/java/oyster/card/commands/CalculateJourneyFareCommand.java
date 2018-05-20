package oyster.card.commands;

import oyster.card.exceptions.NoAvailableFareForJourneyException;
import oyster.card.models.Fare;
import oyster.card.models.Journey;
import oyster.card.queries.GetMinimumZonesCrossedQuery;
import oyster.card.queries.IsZoneOneCrossedQuery;

import java.math.BigDecimal;
import java.util.List;
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
        final boolean isJourneyByBus = journey.getTransportType() == BUS;

        final int minimumCrossedZones = isJourneyByBus ? 0 : getMinimumZonesCrossedQuery.run(journey);
        final boolean isZoneOneCrossed = isJourneyByBus || isZoneOneCrossedQuery.run(journey);

        Predicate<Fare> busTransport = fare -> isJourneyByBus && fare.getTransportType() == BUS;
        Predicate<Fare> zonesCrossed = fare -> fare.getZonesCrossed() == minimumCrossedZones;
        Predicate<Fare> transportType = fare -> fare.getTransportType() == journey.getTransportType();
        Predicate<Fare> isZoneOneAllowed = fare -> fare.isZoneOneIncluded() == isZoneOneCrossed;

        Predicate<Fare> faresFilter =
                busTransport.or(
                        zonesCrossed
                                .and(transportType)
                                .and(isZoneOneAllowed));

        return fares
                .stream()
                .filter(faresFilter)
                .min(comparing(Fare::getValue))
                .map(Fare::getValue)
                .orElseThrow(() -> new NoAvailableFareForJourneyException(journey));
    }
}
