package acceptance.tests.steps;

import commands.CalculateJourneyFareCommand;
import commands.FinishJourneyCommand;
import commands.StartJourneyCommand;
import commands.UpdateCardBalanceCommand;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import exceptions.InsufficientFundsException;
import models.Journey;
import models.Journey.JourneyBuilder;
import queries.GetCardBalanceByUserNameQuery;
import queries.GetMaximumFareQuery;
import queries.GetMinimumZonesCrossedQuery;
import queries.GetStationByNameQuery;
import queries.IsZoneOneCrossedQuery;
import repositories.CardRepository;
import repositories.FareRepository;
import repositories.implementation.InMemoryCardRepository;
import repositories.implementation.InMemoryFareRepository;
import repositories.implementation.InMemoryStationRepository;

import java.math.BigDecimal;

import static models.TransportType.BUS;
import static models.TransportType.TRAIN;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class StepDefinitions {
    private String userName;
    private boolean isUserOutOfFunds = false;
    private final JourneyBuilder journeyBuilder = Journey.builder();

    private final CardRepository inMemoryCardRepository = new InMemoryCardRepository();
    private final FareRepository inMemoryFareRepository = new InMemoryFareRepository();

    private final GetStationByNameQuery getStationByNameQuery = new GetStationByNameQuery(new InMemoryStationRepository());
    private final GetCardBalanceByUserNameQuery getCardBalanceByUserNameQuery = new GetCardBalanceByUserNameQuery(inMemoryCardRepository);
    private final GetMaximumFareQuery getMaximumFareQuery = new GetMaximumFareQuery(inMemoryFareRepository);
    private final GetMinimumZonesCrossedQuery getMinimumZonesCrossedQuery = new GetMinimumZonesCrossedQuery();
    private final IsZoneOneCrossedQuery isZoneOneCrossedQuery = new IsZoneOneCrossedQuery();

    private final CalculateJourneyFareCommand calculateJourneyFareCommand = new CalculateJourneyFareCommand(inMemoryFareRepository, getMinimumZonesCrossedQuery, isZoneOneCrossedQuery);
    private final UpdateCardBalanceCommand updateCardBalanceCommand = new UpdateCardBalanceCommand(inMemoryCardRepository);
    private final StartJourneyCommand startJourneyCommand = new StartJourneyCommand(getCardBalanceByUserNameQuery, updateCardBalanceCommand, getMaximumFareQuery);
    private final FinishJourneyCommand finishJourneyCommand = new FinishJourneyCommand(calculateJourneyFareCommand, updateCardBalanceCommand, getMaximumFareQuery, inMemoryFareRepository);

    @Given("^the user (.*?) has loaded £(\\d+.\\d+) in the card$")
    public void theUserGustavoHasLoadedSomAmountInTheCard(String userName, BigDecimal amount) throws Throwable {
        updateCardBalanceCommand.run(userName, amount);
        this.userName = userName;
    }

    @When("^passes through the inward barrier at the (.*?) station$")
    public void userPassesThroughTheInwardBarrierAtStation(String station) throws Throwable {
        journeyBuilder.origin(getStationByNameQuery.run(station));

        try {
            startJourneyCommand.run(userName);
        } catch (InsufficientFundsException ex) {
            isUserOutOfFunds = true;
        }
    }

    @And("^takes a (.*?)$")
    public void userTakesATrain(String by) throws Throwable {
        journeyBuilder.transportType(by.equals("train") ? TRAIN : BUS);
    }

    @And("^swipes out at the (.*?) station$")
    public void userSwipesOutAtTheStation(String station) throws Throwable {
        journeyBuilder.destination(getStationByNameQuery.run(station));
        finishJourneyCommand.run(userName, journeyBuilder.build());
    }

    @And("^forgets to swipes out at the (.*?) station$")
    public void userForgetsToSwipesOutAtTheStation(String station) throws Throwable {
        journeyBuilder.destination(getStationByNameQuery.run(station));
    }

    @Then("^the card balance is £(\\d+.\\d+)$")
    public void userCardBalanceIs(BigDecimal balance) throws Throwable {
        BigDecimal actualBalance = getCardBalanceByUserNameQuery.run(userName);
        assertThat(actualBalance.doubleValue(), is(balance.doubleValue()));
    }

    @Then("^the barrier does not open because of insufficient funds$")
    public void theBarrierDoesNotOpenBecauseBecauseOfInsufficientFunds() throws Throwable {
        assertThat(isUserOutOfFunds, is(true));
    }
}
