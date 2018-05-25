package acceptance.tests.steps;

import commands.CalculateJourneyFareCommand;
import commands.FinishJourneyCommand;
import commands.SignCardInCommand;
import commands.StartJourneyCommand;
import commands.UpdateCardBalanceCommand;
import models.Journey;
import queries.GetCardBalanceByUserNameQuery;
import queries.GetCardByUserNameQuery;
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
    private final GetMinimumZonesCrossedQuery getMinimumZonesCrossedQuery = new GetMinimumZonesCrossedQuery();
    private final IsZoneOneCrossedQuery isZoneOneCrossedQuery = new IsZoneOneCrossedQuery();

    private final CalculateJourneyFareCommand calculateJourneyFareCommand = new CalculateJourneyFareCommand(inMemoryFareRepository, getMinimumZonesCrossedQuery, isZoneOneCrossedQuery);
    private final UpdateCardBalanceCommand updateCardBalanceCommand = new UpdateCardBalanceCommand(inMemoryCardRepository);
    private final StartJourneyCommand startJourneyCommand = new StartJourneyCommand(getCardBalanceByUserNameQuery, updateCardBalanceCommand, getMaximumFareQuery);
    private final GetCardByUserNameQuery getCardByUserNameQuery = new GetCardByUserNameQuery(inMemoryCardRepository);
    private final FinishJourneyCommand finishJourneyCommand = new FinishJourneyCommand(getCardByUserNameQuery, calculateJourneyFareCommand, updateCardBalanceCommand, getMaximumFareQuery);
    private final SignCardInCommand signCardInCommand = new SignCardInCommand(inMemoryCardRepository);

    void loadCardForUser(String userName, BigDecimal amount) {
        updateCardBalanceCommand.run(userName, amount);
        this.userName = userName;
    }

    void userPassInwardBarrierInStation(String station) {
        journeyBuilder.origin(getStationByNameQuery.run(station));
        signCardInCommand.run(userName);

//        try {
//            isUserOutOfFunds = false;
//            startJourneyCommand.run(userName);
//        } catch (InsufficientFundsException ex) {
//            isUserOutOfFunds = true;
//        }
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
        assertThat(actualBalance.doubleValue(), is(balance.doubleValue()));
    }

    void assertUserHasInsufficientFunds() {
        assertThat(isUserOutOfFunds, is(true));
    }
}
