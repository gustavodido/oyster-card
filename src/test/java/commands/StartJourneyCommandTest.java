package commands;

import exceptions.InsufficientFundsException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import queries.GetCardBalanceByUserNameQuery;
import queries.GetMaximumFareQuery;

import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static stubs.CardStubs.gustavoCard;
import static stubs.FareStubs.anyThreeZones;

@RunWith(MockitoJUnitRunner.class)
public class StartJourneyCommandTest {
    @Mock
    private GetCardBalanceByUserNameQuery getCardBalanceByUserNameQuery;

    @Mock
    private UpdateCardBalanceCommand updateCardBalanceCommand;

    @Mock
    private GetMaximumFareQuery getMaximumFareQuery;

    @InjectMocks
    private StartJourneyCommand startJourneyCommand;

    @Before
    public void setUp() {
        when(getMaximumFareQuery.run()).thenReturn(anyThreeZones());
    }

    @Test
    public void journeyStart_ShouldReduceMaxFareFromCardBalance() {
        when(getMaximumFareQuery.run()).thenReturn(anyThreeZones());
        when(getCardBalanceByUserNameQuery.run(gustavoCard().getUserName())).thenReturn(TEN);

        startJourneyCommand.run(gustavoCard().getUserName());

        verify(updateCardBalanceCommand).run(gustavoCard().getUserName(), anyThreeZones().getValue().negate());
    }

    @Test(expected = InsufficientFundsException.class)
    public void journeyStartWithoutFunds_ShouldThrowException() {
        when(getMaximumFareQuery.run()).thenReturn(anyThreeZones());
        when(getCardBalanceByUserNameQuery.run(gustavoCard().getUserName())).thenReturn(ZERO);

        startJourneyCommand.run(gustavoCard().getUserName());
    }
}