package oyster.card.management.command;

import oyster.card.management.model.Fare;
import oyster.card.management.model.Station;
import oyster.card.management.model.Trip;

import java.math.BigDecimal;
import java.util.List;

public class CalculateTripFareCommand {
    private final List<Station> stations;
    private final List<Fare> fares;

    public CalculateTripFareCommand(List<Station> stations, List<Fare> fares) {
        this.stations = stations;
        this.fares = fares;
    }

    public BigDecimal run(Trip trip) {
        Station origin = stations
                .stream()
                .filter(s -> s.getName().equals(trip.getOrigin().getName()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(".."));

        Station destination = stations
                .stream()
                .filter(s -> s.getName().equals(trip.getDestination().getName()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(".."));

        return BigDecimal.ONE;
    }


}
