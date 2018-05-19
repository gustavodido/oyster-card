package oyster.card.commands;

import oyster.card.models.Fare;
import oyster.card.models.Journey;
import oyster.card.models.Station;

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

