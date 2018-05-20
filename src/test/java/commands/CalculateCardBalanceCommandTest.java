package commands;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static support.FareFactory.anyBusJourney;
import static support.FareFactory.anyOneZoneOutsideZoneOne;
import static support.FareFactory.anywhereInZoneOne;
import static support.JourneyFactory.earlsCourtToChelseaByBus;
import static support.JourneyFactory.earlsCourtToHammersmithByTrain;
import static support.JourneyFactory.holbornToEarlsCourtByTrain;
import static support.JourneyFactory.journeys;

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