package commands;

import models.Journey;
import queries.GetMaximumFareQuery;

import java.math.BigDecimal;

public class FinishJourneyCommand {
    private final CalculateJourneyFareCommand calculateJourneyFareCommand;
    private final UpdateCardBalanceCommand updateCardBalanceCommand;
    private final GetMaximumFareQuery getMaximumFareQuery;

    public FinishJourneyCommand(CalculateJourneyFareCommand calculateJourneyFareCommand,
                                UpdateCardBalanceCommand updateCardBalanceCommand,
                                GetMaximumFareQuery getMaximumFareQuery) {
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
