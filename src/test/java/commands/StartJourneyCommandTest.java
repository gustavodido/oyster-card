package commands;

import exceptions.InsufficientFundsException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import queries.GetBusFareQuery;
import queries.GetCardBalanceByUserNameQuery;
import queries.GetMaximumFareQuery;

import java.math.BigDecimal;

import static java.math.BigDecimal.TEN;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static stubs.CardStubs.gustavoCard;
import static stubs.FareStubs.anyBusJourney;
import static stubs.FareStubs.anyThreeZones;

@RunWith(MockitoJUnitRunner.class)
public class StartJourneyCommandTest {
    @Mock
    private GetCardBalanceByUserNameQuery getCardBalanceByUserNameQuery;

    @Mock
    private UpdateCardBalanceCommand updateCardBalanceCommand;

    @Mock
    private GetMaximumFareQuery getMaximumFareQuery;

    @Mock
    private GetBusFareQuery getBusFareQuery;

    @InjectMocks
    private StartJourneyCommand startJourneyCommand;

    private final BigDecimal LESS_THEN_BUS_FARE  = new BigDecimal(1.79);

    @Before
    public void setUp() {
        when(getMaximumFareQuery.run()).thenReturn(anyThreeZones());
        when(getBusFareQuery.run()).thenReturn(anyBusJourney());
    }

    @Test
    public void journeyStart_ShouldReduceMaxFareFromCardBalance() {
        when(getCardBalanceByUserNameQuery.run(gustavoCard().getUserName())).thenReturn(TEN);

        startJourneyCommand.run(gustavoCard().getUserName());

        verify(updateCardBalanceCommand).run(gustavoCard().getUserName(), anyThreeZones().getValue().negate());
    }

    @Test(expected = InsufficientFundsException.class)
    public void journeyStartWithoutFunds_ShouldThrowException() {
        when(getCardBalanceByUserNameQuery.run(gustavoCard().getUserName())).thenReturn(LESS_THEN_BUS_FARE);

        startJourneyCommand.run(gustavoCard().getUserName());
    }
}