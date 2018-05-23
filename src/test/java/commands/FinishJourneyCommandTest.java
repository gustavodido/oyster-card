package commands;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import queries.GetCardByUserNameQuery;
import queries.GetMaximumFareQuery;

import java.math.BigDecimal;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static stubs.CardStubs.gustavoCard;
import static stubs.CardStubs.gustavoSwipedInCard;
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

    @Mock
    private GetCardByUserNameQuery getCardByUserNameQuery;

    @InjectMocks
    private FinishJourneyCommand finishJourneyCommand;

    @Before
    public void setUp() {
        when(calculateJourneyFareCommand.run(holbornToEarlsCourtByTrain())).thenReturn(anywhereInZoneOne().getValue());
        when(getMaximumFareQuery.run()).thenReturn(anyThreeZones());
    }

    @Test
    public void finishJourneyWithSwipedInCard_ShouldDebitTheFare() {
        when(getCardByUserNameQuery.run(gustavoCard().getUserName())).thenReturn(gustavoSwipedInCard());

        finishJourneyCommand.run(gustavoCard().getUserName(), holbornToEarlsCourtByTrain());

        BigDecimal expectedDebit = anywhereInZoneOne().getValue().negate();

        verify(updateCardBalanceCommand).run(gustavoCard().getUserName(), expectedDebit);
    }

    @Test
    public void finishJourneyWithoutSwipedInCard_ShouldDebitTheMaximumFare() {
        when(getCardByUserNameQuery.run(gustavoCard().getUserName())).thenReturn(gustavoCard());

        finishJourneyCommand.run(gustavoCard().getUserName(), holbornToEarlsCourtByTrain());

        BigDecimal expectedDebit = anyThreeZones().getValue().negate();
        verify(updateCardBalanceCommand).run(gustavoCard().getUserName(), expectedDebit);
    }
}