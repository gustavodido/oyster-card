package oyster.card.management.commands;

import oyster.card.management.models.Fare;
import oyster.card.management.models.Journey;
import oyster.card.management.models.Station;

import java.math.BigDecimal;
import java.util.List;

public class CalculateCardBalanceCommand {
    private final List<Station> stations;
    private final List<Fare> fares;

    public CalculateCardBalanceCommand(List<Station> stations, List<Fare> fares) {
        this.stations = stations;
        this.fares = fares;
    }

    public BigDecimal run(BigDecimal cardBalance, List<Journey> journeys) {
        return BigDecimal.ONE;
    }
}

