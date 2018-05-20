package oyster.card.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import oyster.card.commands.CalculateJourneyFareCommand;
import oyster.card.commands.FinishJourneyCommand;
import oyster.card.commands.StartJourneyCommand;
import oyster.card.commands.UpdateCardBalanceCommand;
import oyster.card.models.Journey;
import oyster.card.models.Journey.JourneyBuilder;
import oyster.card.queries.GetCardBalanceByUserNameQuery;
import oyster.card.queries.GetMaximumFareQuery;
import oyster.card.queries.GetMinimumZonesCrossedQuery;
import oyster.card.queries.GetStationByNameQuery;
import oyster.card.queries.IsZoneOneCrossedQuery;
import oyster.card.repositories.CardRepository;
import oyster.card.repositories.FareRepository;
import oyster.card.repositories.implementation.InMemoryCardRepository;
import oyster.card.repositories.implementation.InMemoryFareRepository;
import oyster.card.repositories.implementation.InMemoryStationRepository;

import java.math.BigDecimal;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static oyster.card.models.TransportType.BUS;
import static oyster.card.models.TransportType.TRAIN;

public class StepDefinitions {
    private String userName;
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
    private final StartJourneyCommand startJourneyCommand = new StartJourneyCommand(updateCardBalanceCommand, getMaximumFareQuery);
    private final FinishJourneyCommand finishJourneyCommand = new FinishJourneyCommand(calculateJourneyFareCommand, updateCardBalanceCommand, getMaximumFareQuery, inMemoryFareRepository);


    @Given("^the user (.*?) has loaded £(\\d+.\\d+) in his card$")
    public void theUserGustavoHasLoadedSomAmountInHisCard(String userName, BigDecimal amount) throws Throwable {
        updateCardBalanceCommand.run(userName, amount);
        this.userName = userName;
    }

    @When("^he passes through the inward barrier at the (.*?) station$")
    public void hePassesThroughTheInwardBarrierAtStation(String station) throws Throwable {
        journeyBuilder.origin(getStationByNameQuery.run(station));
        startJourneyCommand.run(userName);

    }

    @And("^he takes a (.*?)$")
    public void heTakesATrain(String by) throws Throwable {
        journeyBuilder.transportType(by.equals("train") ? TRAIN : BUS);
    }

    @And("^he swipes out at the (.*?)$")
    public void heSwipesOutAtTheStation(String station) throws Throwable {
        journeyBuilder.destination(getStationByNameQuery.run(station));
        finishJourneyCommand.run(userName, journeyBuilder.build());
    }

    @Then("^his card balance is £(\\d+.\\d+)$")
    public void hisCardBalanceIs(BigDecimal balance) throws Throwable {
        BigDecimal actualBalance = getCardBalanceByUserNameQuery.run(userName);
        assertThat(actualBalance.doubleValue(), is(balance.doubleValue()));
    }
}
