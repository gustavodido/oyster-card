package commands;

import exceptions.InsufficientFundsException;
import models.Fare;
import queries.GetBusFareQuery;
import queries.GetCardBalanceByUserNameQuery;
import queries.GetMaximumFareQuery;

import java.math.BigDecimal;

import static java.math.RoundingMode.HALF_UP;

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
        BigDecimal busFare = getBusFareQuery.run()
                .getValue()
                .setScale(2, HALF_UP);

        BigDecimal currentBalance = getCardBalanceByUserNameQuery
                .run(userName)
                .setScale(2, HALF_UP);

        if (busFare.compareTo(currentBalance) == 1) {
            throw new InsufficientFundsException(userName, currentBalance);
        }

        Fare maxFare = getMaximumFareQuery.run();
        updateCardBalanceCommand.run(userName, maxFare.getValue().negate());
    }
}
