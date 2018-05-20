package oyster.card.commands;

import oyster.card.models.Journey;
import oyster.card.queries.GetMaximumFareQuery;
import oyster.card.repositories.FareRepository;

import java.math.BigDecimal;

public class FinishJourneyCommand {
    private final CalculateJourneyFareCommand calculateJourneyFareCommand;
    private final UpdateCardBalanceCommand updateCardBalanceCommand;
    private final GetMaximumFareQuery getMaximumFareQuery;

    public FinishJourneyCommand(CalculateJourneyFareCommand calculateJourneyFareCommand, UpdateCardBalanceCommand updateCardBalanceCommand, GetMaximumFareQuery getMaximumFareQuery, FareRepository fareRepository) {
        this.calculateJourneyFareCommand = calculateJourneyFareCommand;
        this.updateCardBalanceCommand = updateCardBalanceCommand;
        this.getMaximumFareQuery = getMaximumFareQuery;
    }

    public void run(String userName, Journey journey) {
        BigDecimal maxFare = getMaximumFareQuery.run().getValue();
        BigDecimal journeyFare = calculateJourneyFareCommand.run(journey);

        updateCardBalanceCommand.run(userName, maxFare.subtract(journeyFare));
    }
}
