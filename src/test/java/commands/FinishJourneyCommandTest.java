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
import static stubs.CardStubs.signedInGustavoCard;
import static stubs.FareStubs.anyThreeZones;
import static stubs.FareStubs.anywhereInZoneOne;
import static stubs.JourneyStubs.holbornToEarlsCourtByTrain;

@RunWith(MockitoJUnitRunner.class)
public class FinishJourneyCommandTest {
    @Mock
    private GetCardByUserNameQuery getCardByUserNameQuery;

    @Mock
    private CalculateJourneyFareCommand calculateJourneyFareCommand;

    @Mock
    private UpdateCardBalanceCommand updateCardBalanceCommand;

    @Mock
    private GetMaximumFareQuery getMaximumFareQuery;

    @InjectMocks
    private FinishJourneyCommand finishJourneyCommand;

    @Before
    public void setUp() {
        when(calculateJourneyFareCommand.run(holbornToEarlsCourtByTrain())).thenReturn(anywhereInZoneOne().getValue());
        when(getMaximumFareQuery.run()).thenReturn(anyThreeZones());
    }

    @Test
    public void finishJourneyWithSignedInCard_ShouldDebitJourneyFare() {
        when(getCardByUserNameQuery.run(gustavoCard().getUserName())).thenReturn(signedInGustavoCard());

        finishJourneyCommand.run(gustavoCard().getUserName(), holbornToEarlsCourtByTrain());

        BigDecimal expectedDebit = anywhereInZoneOne().getValue().negate();
        verify(updateCardBalanceCommand).run(gustavoCard().getUserName(), expectedDebit);
    }

    @Test
    public void finishJourneyWithoutSignedInCard_ShouldDebitMaximumFare() {
        when(getCardByUserNameQuery.run(gustavoCard().getUserName())).thenReturn(gustavoCard());

        finishJourneyCommand.run(gustavoCard().getUserName(), holbornToEarlsCourtByTrain());

        BigDecimal expectedDebit = anyThreeZones().getValue().negate();
        verify(updateCardBalanceCommand).run(gustavoCard().getUserName(), expectedDebit);
    }

    }