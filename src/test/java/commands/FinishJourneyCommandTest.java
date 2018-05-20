package commands;

import support.CardFactory;
import support.FareFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import queries.GetMaximumFareQuery;

import java.math.BigDecimal;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static support.JourneyFactory.holbornToEarlsCourtByTrain;

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
        when(calculateJourneyFareCommand.run(holbornToEarlsCourtByTrain())).thenReturn(FareFactory.anywhereInZoneOne().getValue());
        when(getMaximumFareQuery.run()).thenReturn(FareFactory.anyThreeZones());

        finishJourneyCommand.run(CardFactory.gustavoCard().getUserName(), holbornToEarlsCourtByTrain());

        BigDecimal expectedCredit = FareFactory.anyThreeZones().getValue()
                .subtract(FareFactory.anywhereInZoneOne().getValue());

        verify(updateCardBalanceCommand).run(CardFactory.gustavoCard().getUserName(), expectedCredit);
    }

}