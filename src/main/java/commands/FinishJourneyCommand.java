package commands;

import models.Card;
import models.Journey;
import queries.GetCardByUserNameQuery;
import queries.GetMaximumFareQuery;

import java.math.BigDecimal;

public class FinishJourneyCommand {
    private final GetCardByUserNameQuery getCardByUserNameQuery;
    private final CalculateJourneyFareCommand calculateJourneyFareCommand;
    private final UpdateCardBalanceCommand updateCardBalanceCommand;
    private final GetMaximumFareQuery getMaximumFareQuery;

    public FinishJourneyCommand(GetCardByUserNameQuery getCardByUserNameQuery, CalculateJourneyFareCommand calculateJourneyFareCommand,
                                UpdateCardBalanceCommand updateCardBalanceCommand,
                                GetMaximumFareQuery getMaximumFareQuery) {
        this.getCardByUserNameQuery = getCardByUserNameQuery;
        this.calculateJourneyFareCommand = calculateJourneyFareCommand;
        this.updateCardBalanceCommand = updateCardBalanceCommand;
        this.getMaximumFareQuery = getMaximumFareQuery;
    }

    public void run(String userName, Journey journey) {
        Card card = getCardByUserNameQuery.run(userName);

        BigDecimal journeyFare = card.isSignedIn() ?
                calculateJourneyFareCommand.run(journey) :
                getMaximumFareQuery.run().getValue();

        updateCardBalanceCommand.run(userName, journeyFare.negate());
    }
}
