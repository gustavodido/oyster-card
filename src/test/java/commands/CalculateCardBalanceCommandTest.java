package commands;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static stubs.FareStubs.anyBusJourney;
import static stubs.FareStubs.anyOneZoneOutsideZoneOne;
import static stubs.FareStubs.anywhereInZoneOne;
import static stubs.JourneyStubs.earlsCourtToChelseaByBus;
import static stubs.JourneyStubs.earlsCourtToHammersmithByTrain;
import static stubs.JourneyStubs.holbornToEarlsCourtByTrain;
import static stubs.JourneyStubs.journeys;

@RunWith(MockitoJUnitRunner.class)
public class CalculateCardBalanceCommandTest {
    @Mock
    private CalculateJourneyFareCommand calculateJourneyFareCommand;

    @InjectMocks
    private CalculateCardBalanceCommand calculateCardBalanceCommand;

    @Test
    public void journeyFares_ShouldBeDeductedFromBalance() {
        when(calculateJourneyFareCommand.run(holbornToEarlsCourtByTrain()))
                .thenReturn(anywhereInZoneOne().getValue());

        when(calculateJourneyFareCommand.run(earlsCourtToChelseaByBus()))
                .thenReturn(anyBusJourney().getValue());

        when(calculateJourneyFareCommand.run(earlsCourtToHammersmithByTrain()))
                .thenReturn(anyOneZoneOutsideZoneOne().getValue());

        final BigDecimal actualBalance = new BigDecimal(30.00);

        final BigDecimal expectedBalance = actualBalance
                .min(anywhereInZoneOne().getValue())
                .min(anyBusJourney().getValue())
                .min(anyOneZoneOutsideZoneOne().getValue());

        BigDecimal newBalance = calculateCardBalanceCommand.run(actualBalance, journeys());

        assertThat(newBalance, is(expectedBalance));
    }
}