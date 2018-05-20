package oyster.card.commands;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import oyster.card.queries.GetMaximumFareQuery;

import java.math.BigDecimal;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static oyster.card.support.CardFactory.gustavoCard;
import static oyster.card.support.FareFactory.anyThreeZones;
import static oyster.card.support.FareFactory.anywhereInZoneOne;
import static oyster.card.support.JourneyFactory.holbornToEarlsCourtByTrain;

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