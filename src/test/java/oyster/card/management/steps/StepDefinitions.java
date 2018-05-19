package oyster.card.management.steps;

import cucumber.api.java.en.Given;
import org.junit.Assert;

import static org.hamcrest.core.Is.is;

public class StepDefinitions {
    @Given("^I have (\\d+) cukes in my belly$")
    public void I_have_cukes_in_my_belly(int cukes) throws Throwable {
        Assert.assertThat(cukes, is(42));
    }
}
