package oyster.card.management.commands;

import oyster.card.management.models.Fare;
import oyster.card.management.models.TransportType;
import oyster.card.management.models.Trip;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;

class CalculateTripFareCommand {
    private final List<Fare> fares;

    CalculateTripFareCommand(List<Fare> fares) {
        this.fares = fares;
    }

    BigDecimal run(Trip trip) {
        CalculateMinimumZonesCrossedCommand calculateMinimumZonesCrossedCommand = new CalculateMinimumZonesCrossedCommand();
        int minimumCrossedZones = calculateMinimumZonesCrossedCommand.run(trip);

        List<Integer> tripZones = Stream.concat(
                trip.getOrigin().getZones().stream(),
                trip.getDestination().getZones().stream())
                .collect(Collectors.toList());

        Optional<Fare> minimumFare = fares.stream().filter(f ->
                (trip.getTransportType() == TransportType.BUS && f.getTransportType() == trip.getTransportType())
                || (
                f.getZonesCrossed() == minimumCrossedZones
                && !isExcluded(f, tripZones)
                && f.getTransportType() == trip.getTransportType()))
                .min(comparing(Fare::getValue));

        return minimumFare.get().getValue();
    }

    private boolean isExcluded(Fare fare, List<Integer> zones) {
        for (int excludedZone: fare.getExcludingZones()) {
            for (int zone : zones) {
                if (excludedZone == zone)
                    return true;
            }
        }

        return false;
    }
}
