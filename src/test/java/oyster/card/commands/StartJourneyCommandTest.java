package oyster.card.commands;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import oyster.card.repositories.FareRepository;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static oyster.card.support.CardFactory.gustavoCard;
import static oyster.card.support.FareFactory.anyBusJourney;
import static oyster.card.support.FareFactory.anyThreeZones;
import static oyster.card.support.FareFactory.anywhereInZoneOne;

@RunWith(MockitoJUnitRunner.class)
public class StartJourneyCommandTest {
    @Mock
    private UpdateCardBalanceCommand updateCardBalanceCommand;

    @Mock
    private FareRepository fareRepository;

    @InjectMocks
    private StartJourneyCommand startJourneyCommand;

    @Test
    public void journeyStart_ShouldReduceMaxFareFromCardBalance() {
        when(fareRepository.list()).thenReturn(asList(anyBusJourney(), anyThreeZones(), anywhereInZoneOne()));

        startJourneyCommand.run(gustavoCard().getUserName());

        verify(updateCardBalanceCommand).run(gustavoCard().getUserName(), anyThreeZones().getValue().negate());
    }

    @Test
    public void journeyStart_ShouldDoNothingIfThereIsNoFares() {
        when(fareRepository.list()).thenReturn(emptyList());

        startJourneyCommand.run(gustavoCard().getUserName());

        verify(updateCardBalanceCommand, never()).run(any(), any());
    }
}