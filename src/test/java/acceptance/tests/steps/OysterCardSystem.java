package acceptance.tests.steps;

import commands.CalculateJourneyFareCommand;
import commands.FinishJourneyCommand;
import commands.StartJourneyCommand;
import commands.UpdateCardBalanceCommand;
import exceptions.InsufficientFundsException;
import models.Journey;
import queries.GetBusFareQuery;
import queries.GetCardBalanceByUserNameQuery;
import queries.GetMaximumFareQuery;
import queries.GetMinimumZonesCrossedQuery;
import queries.GetStationByNameQuery;
import queries.IsZoneOneCrossedQuery;
import repositories.CardRepository;
import repositories.FareRepository;
import repositories.StationRepository;
import repositories.implementation.InMemoryCardRepository;
import repositories.implementation.InMemoryFareRepository;
import repositories.implementation.InMemoryStationRepository;

import java.math.BigDecimal;

import static models.TransportType.BUS;
import static models.TransportType.TRAIN;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.number.BigDecimalCloseTo.closeTo;
import static org.junit.Assert.assertThat;

class OysterCardSystem {
    private String userName;
    private boolean isUserOutOfFunds = false;
    private final Journey.JourneyBuilder journeyBuilder = Journey.builder();

    private final CardRepository inMemoryCardRepository = new InMemoryCardRepository();
    private final FareRepository inMemoryFareRepository = new InMemoryFareRepository();
    private final StationRepository inMemoryStationRepository = new InMemoryStationRepository();

    private final GetStationByNameQuery getStationByNameQuery = new GetStationByNameQuery(inMemoryStationRepository);
    private final GetCardBalanceByUserNameQuery getCardBalanceByUserNameQuery = new GetCardBalanceByUserNameQuery(inMemoryCardRepository);
    private final GetMaximumFareQuery getMaximumFareQuery = new GetMaximumFareQuery(inMemoryFareRepository);
    private final GetBusFareQuery getBusFareQuery = new GetBusFareQuery(inMemoryFareRepository);
    private final GetMinimumZonesCrossedQuery getMinimumZonesCrossedQuery = new GetMinimumZonesCrossedQuery();
    private final IsZoneOneCrossedQuery isZoneOneCrossedQuery = new IsZoneOneCrossedQuery();

    private final CalculateJourneyFareCommand calculateJourneyFareCommand = new CalculateJourneyFareCommand(inMemoryFareRepository, getMinimumZonesCrossedQuery, isZoneOneCrossedQuery);
    private final UpdateCardBalanceCommand updateCardBalanceCommand = new UpdateCardBalanceCommand(inMemoryCardRepository);
    private final StartJourneyCommand startJourneyCommand = new StartJourneyCommand(getCardBalanceByUserNameQuery, updateCardBalanceCommand, getMaximumFareQuery, getBusFareQuery);
    private final FinishJourneyCommand finishJourneyCommand = new FinishJourneyCommand(calculateJourneyFareCommand, updateCardBalanceCommand, getMaximumFareQuery);

    void loadCardForUser(String userName, BigDecimal amount) {
        updateCardBalanceCommand.run(userName, amount);
        this.userName = userName;
    }

    void userPassInwardBarrierInStation(String station) {
        journeyBuilder.origin(getStationByNameQuery.run(station));

        try {
            isUserOutOfFunds = false;
            startJourneyCommand.run(userName);
        } catch (InsufficientFundsException ex) {
            isUserOutOfFunds = true;
        }
    }

    void userTakesTransport(String by) {
        journeyBuilder.transportType(by.equals("train") ? TRAIN : BUS);
    }

    void userSwipesOutCardAtStation(String station) {
        journeyBuilder.destination(getStationByNameQuery.run(station));
        finishJourneyCommand.run(userName, journeyBuilder.build());
    }

    void assertUserBalanceIs(BigDecimal balance) {
        BigDecimal actualBalance = getCardBalanceByUserNameQuery.run(userName);
        assertThat(actualBalance, closeTo(balance, new BigDecimal("0.03")));
    }

    void assertUserHasInsufficientFunds() {
        assertThat(isUserOutOfFunds, is(true));
    }
}
