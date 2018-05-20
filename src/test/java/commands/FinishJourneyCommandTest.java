package commands;

import stubs.CardStubs;
import stubs.FareStubs;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import queries.GetMaximumFareQuery;
import java.math.BigDecimal;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
        when(calculateJourneyFareCommand.run(holbornToEarlsCourtByTrain())).thenReturn(FareStubs.anywhereInZoneOne().getValue());
        when(getMaximumFareQuery.run()).thenReturn(FareStubs.anyThreeZones());

        finishJourneyCommand.run(CardStubs.gustavoCard().getUserName(), holbornToEarlsCourtByTrain());

        BigDecimal expectedCredit = FareStubs.anyThreeZones().getValue()
                .subtract(FareStubs.anywhereInZoneOne().getValue());

        verify(updateCardBalanceCommand).run(CardStubs.gustavoCard().getUserName(), expectedCredit);
    }

}