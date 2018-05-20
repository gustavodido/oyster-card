package commands;

import exceptions.InsufficientFundsException;
import models.Fare;
import queries.GetCardBalanceByUserNameQuery;
import queries.GetMaximumFareQuery;

import java.math.BigDecimal;

public class StartJourneyCommand {
    private final GetCardBalanceByUserNameQuery getCardBalanceByUserNameQuery;
    private final UpdateCardBalanceCommand updateCardBalanceCommand;
    private final GetMaximumFareQuery getMaximumFareQuery;

    public StartJourneyCommand(GetCardBalanceByUserNameQuery getCardBalanceByUserNameQuery, UpdateCardBalanceCommand updateCardBalanceCommand, GetMaximumFareQuery getMaximumFareQuery) {
        this.getCardBalanceByUserNameQuery = getCardBalanceByUserNameQuery;
        this.updateCardBalanceCommand = updateCardBalanceCommand;
        this.getMaximumFareQuery = getMaximumFareQuery;
    }

    public void run(String userName) {
        Fare maxFare = getMaximumFareQuery.run();

        BigDecimal currentBalance = getCardBalanceByUserNameQuery.run(userName);
        if (maxFare.getValue().compareTo(currentBalance) == 1) {
            throw new InsufficientFundsException(userName, currentBalance);
        }

        updateCardBalanceCommand.run(userName, maxFare.getValue().negate());
    }
}
