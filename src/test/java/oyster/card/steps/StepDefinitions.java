package oyster.card.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import oyster.card.commands.LoadCardCommand;
import oyster.card.models.Journey;
import oyster.card.models.Journey.JourneyBuilder;
import oyster.card.queries.GetStationByNameQuery;
import oyster.card.repositories.implementation.InMemoryCardRepository;
import oyster.card.repositories.implementation.InMemoryStationRepository;

import java.math.BigDecimal;

import static oyster.card.models.TransportType.BUS;
import static oyster.card.models.TransportType.TRAIN;

public class StepDefinitions {
    private String userName;
    private JourneyBuilder journeyBuilder = Journey.builder();

    private LoadCardCommand loadCardCommand = new LoadCardCommand(new InMemoryCardRepository());
    private GetStationByNameQuery getStationByNameQuery = new GetStationByNameQuery(new InMemoryStationRepository());

    @Given("^the user (.*?) has loaded £(\\d+.\\d+) in his card$")
    public void theUserGustavoHasLoadedSomAmountInHisCard(String userName, BigDecimal amount) throws Throwable {
        loadCardCommand.run(userName, amount);
        this.userName = userName;
    }

    @When("^he passes through the inward barrier at the (.*?) station$")
    public void hePassesThroughTheInwardBarrierAtStation(String station) throws Throwable {
        journeyBuilder.origin(getStationByNameQuery.run(station));
    }

    @And("^he takes a (.*?)$")
    public void heTakesATrain(String by) throws Throwable {
        journeyBuilder.transportType(by.equals("train") ? TRAIN : BUS);
    }

    @And("^he swipes out at the (.*?)$")
    public void heSwipesOutAtTheStation(String station) throws Throwable {
        journeyBuilder.destination(getStationByNameQuery.run(station));
    }

    @Then("^his card balance is £(\\d+.\\d+)$")
    public void hisCardBalanceIs(double amount) throws Throwable {
    }
}
