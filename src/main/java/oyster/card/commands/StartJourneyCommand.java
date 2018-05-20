package oyster.card.commands;

import oyster.card.models.Fare;
import oyster.card.queries.GetMaximumFareQuery;

public class StartJourneyCommand {
    private final UpdateCardBalanceCommand updateCardBalanceCommand;
    private final GetMaximumFareQuery getMaximumFareQuery;

    public StartJourneyCommand(UpdateCardBalanceCommand updateCardBalanceCommand, GetMaximumFareQuery getMaximumFareQuery) {
        this.updateCardBalanceCommand = updateCardBalanceCommand;
        this.getMaximumFareQuery = getMaximumFareQuery;
    }

    public void run(String userName) {
        Fare maxFare = getMaximumFareQuery.run();
        updateCardBalanceCommand.run(userName, maxFare.getValue().negate());
    }
}
