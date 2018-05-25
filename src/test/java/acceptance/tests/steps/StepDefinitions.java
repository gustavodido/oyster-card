package acceptance.tests.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.math.BigDecimal;

public class StepDefinitions {
    private final OysterCardSystem oysterCardSystem = new OysterCardSystem();

    @Given("^the user (.*?) has loaded £(\\d+.\\d+) in the card$")
    public void theUserGustavoHasLoadedSomAmountInTheCard(String userName, BigDecimal amount) throws Throwable {
        oysterCardSystem.loadCardForUser(userName, amount);
    }

    @When("^passes through the inward barrier at the (.*?) station$")
    public void userPassesThroughTheInwardBarrierAtStation(String station) throws Throwable {
        oysterCardSystem.userPassInwardBarrierInStation(station, true);
    }

    @When("^passes through the inward barrier at the (.*?) station without signing in$")
    public void passesThroughTheInwardBarrierAtTheHolbornStationWithoutSigningIn(String station) throws Throwable {
        oysterCardSystem.userPassInwardBarrierInStation(station, false);
    }

    @And("^takes a (.*?)$")
    public void userTakesTransport(String by) throws Throwable {
        oysterCardSystem.userTakesTransport(by);
    }

    @And("^swipes out at the (.*?) station$")
    public void userSwipesOutAtTheStation(String station) throws Throwable {
        oysterCardSystem.userSwipesOutCardAtStation(station);
    }

    @And("^forgets to swipes out at the (.*?) station$")
    public void userForgetsToSwipesOutAtTheStation(String station) throws Throwable {
        // Don't need to do anything
    }

    @Then("^the card balance is £(\\d+.\\d+)$")
    public void userCardBalanceIs(BigDecimal balance) throws Throwable {
        oysterCardSystem.assertUserBalanceIs(balance);
    }

    @Then("^the barrier does not open because of insufficient funds$")
    public void theBarrierDoesNotOpenBecauseBecauseOfInsufficientFunds() throws Throwable {
        oysterCardSystem.assertUserHasInsufficientFunds();
    }
}
