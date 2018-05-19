package oyster.card.commands;

import oyster.card.models.Fare;
import oyster.card.models.TransportType;
import oyster.card.models.Journey;
import oyster.card.queries.GetMinimumZonesCrossedQuery;
import oyster.card.queries.IsZoneOneCrossedQuery;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static java.util.Comparator.comparing;

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
