package oyster.card.management.commands;

import oyster.card.management.models.Fare;
import oyster.card.management.models.Station;
import oyster.card.management.models.Trip;

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
        return BigDecimal.ONE;
    }


}
