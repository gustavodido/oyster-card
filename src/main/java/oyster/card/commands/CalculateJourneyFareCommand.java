package oyster.card.commands;

import oyster.card.exceptions.NoAvailableFareForJourneyException;
import oyster.card.models.Fare;
import oyster.card.models.Journey;
import oyster.card.queries.GetMinimumZonesCrossedQuery;
import oyster.card.queries.IsZoneOneCrossedQuery;
import oyster.card.repositories.FareRepository;

import java.math.BigDecimal;
import java.util.function.Predicate;

import static java.util.Comparator.comparing;
import static oyster.card.models.TransportType.BUS;

public class CalculateJourneyFareCommand {
    private final GetMinimumZonesCrossedQuery getMinimumZonesCrossedQuery;
    private final IsZoneOneCrossedQuery isZoneOneCrossedQuery;
    private final FareRepository fareRepository;

    public CalculateJourneyFareCommand(FareRepository fareRepository, GetMinimumZonesCrossedQuery getMinimumZonesCrossedQuery, IsZoneOneCrossedQuery isZoneOneCrossedQuery) {
        this.fareRepository = fareRepository;
        this.getMinimumZonesCrossedQuery = getMinimumZonesCrossedQuery;
        this.isZoneOneCrossedQuery = isZoneOneCrossedQuery;
    }

    public BigDecimal run(Journey journey) {
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

        return fareRepository
                .list()
                .stream()
                .filter(faresFilter)
                .min(comparing(Fare::getValue))
                .map(Fare::getValue)
                .orElseThrow(() -> new NoAvailableFareForJourneyException(journey));
    }
}
