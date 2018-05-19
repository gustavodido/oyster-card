package oyster.card.management.command;

import oyster.card.management.model.Fare;
import oyster.card.management.model.Trip;
import oyster.card.management.model.Station;

import java.math.BigDecimal;
import java.util.List;

public class CalculateCardBalanceCommand {
    private final List<Station> stations;
    private final List<Fare> fares;

    public CalculateCardBalanceCommand(List<Station> stations, List<Fare> fares) {
        this.stations = stations;
        this.fares = fares;
    }

    public BigDecimal run(BigDecimal cardBalance, List<Trip> trips) {
        return BigDecimal.ONE;
    }
}

