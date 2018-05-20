package oyster.card.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import oyster.card.commands.LoadCardCommand;
import oyster.card.models.Card;
import oyster.card.repositories.implementation.InMemoryCardRepository;

import java.math.BigDecimal;

public class StepDefinitions {
    private Card currentUserCard;
    private LoadCardCommand loadCardCommand = new LoadCardCommand(new InMemoryCardRepository());

    @Given("^The oyster card system has the stations created$")
    public void theOysterCardSystemHasTheStationsCreated() throws Throwable {
    }

    @Given("^the user (.*?) has loaded £(\\d+.\\d+) in his card$")
    public void theUserGustavoHasLoadedSomAmountInHisCard(String userName, BigDecimal amount) throws Throwable {
        currentUserCard = loadCardCommand.run(userName, amount);
    }

    @When("^he goes from (.*?) to (.*?) by (.*?)$")
    public void heGoesFromHolbornToEarlSCourtByTrain(String from, String to, String by) throws Throwable {

    }

    @Then("^his card balance is £(\\d+.\\d+)$")
    public void hisCardBalanceIs(double amount) throws Throwable {
    }
}
