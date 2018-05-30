package commands;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import queries.GetMaximumFareQuery;

import java.math.BigDecimal;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static stubs.CardStubs.gustavoCard;
import static stubs.FareStubs.anyThreeZones;
import static stubs.FareStubs.anywhereInZoneOne;
import static stubs.JourneyStubs.holbornToEarlsCourtByTrain;

@RunWith(MockitoJUnitRunner.class)
public class FinishJourneyCommandTest {
    @Mock
    private CalculateJourneyFareCommand calculateJourneyFareCommand;

    @Mock
    private UpdateCardBalanceCommand updateCardBalanceCommand;

    @Mock
    private GetMaximumFareQuery getMaximumFareQuery;

    @InjectMocks
    private FinishJourneyCommand finishJourneyCommand;

    @Test
    public void finishJourney_ShouldCreditTheFareDifference() {
        when(calculateJourneyFareCommand.run(holbornToEarlsCourtByTrain())).thenReturn(anywhereInZoneOne().getValue());
        when(getMaximumFareQuery.run()).thenReturn(anyThreeZones());

        finishJourneyCommand.run(gustavoCard().getUserName(), holbornToEarlsCourtByTrain());

        BigDecimal expectedCredit = anyThreeZones().getValue()
                .subtract(anywhereInZoneOne().getValue());

        verify(updateCardBalanceCommand).run(gustavoCard().getUserName(), expectedCredit);
    }

}