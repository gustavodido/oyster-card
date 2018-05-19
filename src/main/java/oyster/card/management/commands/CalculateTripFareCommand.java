package oyster.card.management.commands;

import oyster.card.management.models.Fare;
import oyster.card.management.models.TransportType;
import oyster.card.management.models.Trip;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static java.util.Comparator.comparing;

class CalculateTripFareCommand {
    private final List<Fare> fares;

    CalculateTripFareCommand(List<Fare> fares) {
        this.fares = fares;
    }

    BigDecimal run(Trip trip) {
        CalculateMinimumZonesCrossedCommand calculateMinimumZonesCrossedCommand = new CalculateMinimumZonesCrossedCommand();
        int minimumCrossedZones = calculateMinimumZonesCrossedCommand.run(trip);

        boolean isZoneOneCrossed = (trip.getOrigin().getZones().size() == 1 && trip.getOrigin().getZones().contains(1)) ||
                (trip.getDestination().getZones().size() == 1 && trip.getDestination().getZones().contains(1));

        Optional<Fare> minimumFare = fares.stream().filter(f ->
                (trip.getTransportType() == TransportType.BUS && f.getTransportType() == trip.getTransportType())
                || (
                f.getZonesCrossed() == minimumCrossedZones
                && f.isCanIncludeZoneOne() == isZoneOneCrossed
                && f.getTransportType() == trip.getTransportType()))
                .min(comparing(Fare::getValue));

        return minimumFare.get().getValue();
    }
}
