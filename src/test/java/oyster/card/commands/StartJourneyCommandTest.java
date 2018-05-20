package oyster.card.commands;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import oyster.card.queries.GetMaximumFareQuery;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static oyster.card.support.CardFactory.gustavoCard;
import static oyster.card.support.FareFactory.anyThreeZones;

@RunWith(MockitoJUnitRunner.class)
public class StartJourneyCommandTest {
    @Mock
    private UpdateCardBalanceCommand updateCardBalanceCommand;

    @Mock
    private GetMaximumFareQuery getMaximumFareQuery;

    @InjectMocks
    private StartJourneyCommand startJourneyCommand;

    @Test
    public void journeyStart_ShouldReduceMaxFareFromCardBalance() {
        when(getMaximumFareQuery.run()).thenReturn(anyThreeZones());

        startJourneyCommand.run(gustavoCard().getUserName());

        verify(updateCardBalanceCommand).run(gustavoCard().getUserName(), anyThreeZones().getValue().negate());
    }
}