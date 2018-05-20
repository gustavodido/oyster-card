package oyster.card.commands;

import oyster.card.exceptions.InvalidCardBalanceException;
import oyster.card.models.Journey;

import java.math.BigDecimal;
import java.util.List;

import static java.math.BigDecimal.ZERO;

public class CalculateCardBalanceCommand {
    private final CalculateJourneyFareCommand calculateJourneyFareCommand;

    public CalculateCardBalanceCommand(CalculateJourneyFareCommand calculateJourneyFareCommand) {
        this.calculateJourneyFareCommand = calculateJourneyFareCommand;
    }

    public BigDecimal run(BigDecimal actualBalance, List<Journey> journeys) {
        BigDecimal newCardBalance = journeys
                .stream()
                .reduce(actualBalance,
                        (balance, journey) -> balance.min(calculateJourneyFareCommand.run(journey)),
                        (balance, journey) -> balance);

        if (newCardBalance.compareTo(ZERO) == -1) {
            throw new InvalidCardBalanceException(newCardBalance);
        }

        return newCardBalance;
    }
}

