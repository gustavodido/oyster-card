package oyster.card.management.commands;

import oyster.card.management.models.Fare;
import oyster.card.management.models.TransportType;
import oyster.card.management.models.Journey;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static java.util.Comparator.comparing;

class CalculateJourneyFareCommand {
    private final List<Fare> fares;

    CalculateJourneyFareCommand(List<Fare> fares) {
        this.fares = fares;
    }

    BigDecimal run(Journey journey) {
        CalculateMinimumZonesCrossedCommand calculateMinimumZonesCrossedCommand = new CalculateMinimumZonesCrossedCommand();
        int minimumCrossedZones = calculateMinimumZonesCrossedCommand.run(journey);

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
