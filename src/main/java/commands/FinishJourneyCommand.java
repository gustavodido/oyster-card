package commands;

import models.Journey;
import queries.GetCardByUserNameQuery;
import queries.GetMaximumFareQuery;

import java.math.BigDecimal;

public class FinishJourneyCommand {
    private final CalculateJourneyFareCommand calculateJourneyFareCommand;
    private final UpdateCardBalanceCommand updateCardBalanceCommand;
    private final GetMaximumFareQuery getMaximumFareQuery;
    private final GetCardByUserNameQuery getCardByUserNameQuery;

    public FinishJourneyCommand(CalculateJourneyFareCommand calculateJourneyFareCommand,
                                UpdateCardBalanceCommand updateCardBalanceCommand,
                                GetMaximumFareQuery getMaximumFareQuery, GetCardByUserNameQuery getCardByUserNameQuery) {
        this.calculateJourneyFareCommand = calculateJourneyFareCommand;
        this.updateCardBalanceCommand = updateCardBalanceCommand;
        this.getMaximumFareQuery = getMaximumFareQuery;
        this.getCardByUserNameQuery = getCardByUserNameQuery;
    }

    public void run(String userName, Journey journey) {
        BigDecimal maxFare = getMaximumFareQuery.run().getValue();
        BigDecimal journeyFare = calculateJourneyFareCommand.run(journey);

        BigDecimal fare = getCardByUserNameQuery.run(userName).isSwipedIn() ?
                journeyFare :
                maxFare;

        updateCardBalanceCommand.run(userName, fare.negate());
    }
}
