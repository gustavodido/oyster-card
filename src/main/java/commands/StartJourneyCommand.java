package commands;

import exceptions.InsufficientFundsException;
import models.Fare;
import queries.GetBusFareQuery;
import queries.GetCardBalanceByUserNameQuery;
import queries.GetMaximumFareQuery;

import java.math.BigDecimal;

public class StartJourneyCommand {
    private final GetCardBalanceByUserNameQuery getCardBalanceByUserNameQuery;
    private final UpdateCardBalanceCommand updateCardBalanceCommand;
    private final GetMaximumFareQuery getMaximumFareQuery;
    private final GetBusFareQuery getBusFareQuery;

    public StartJourneyCommand(GetCardBalanceByUserNameQuery getCardBalanceByUserNameQuery, UpdateCardBalanceCommand updateCardBalanceCommand, GetMaximumFareQuery getMaximumFareQuery, GetBusFareQuery getBusFareQuery) {
        this.getCardBalanceByUserNameQuery = getCardBalanceByUserNameQuery;
        this.updateCardBalanceCommand = updateCardBalanceCommand;
        this.getMaximumFareQuery = getMaximumFareQuery;
        this.getBusFareQuery = getBusFareQuery;
    }

    public void run(String userName) {
        Fare busFare = getBusFareQuery.run();

        BigDecimal currentBalance = getCardBalanceByUserNameQuery.run(userName);
        if (busFare.getValue().compareTo(currentBalance) == 1) {
            throw new InsufficientFundsException(userName, currentBalance);
        }

        Fare maxFare = getMaximumFareQuery.run();
        updateCardBalanceCommand.run(userName, maxFare.getValue().negate());
    }
}
