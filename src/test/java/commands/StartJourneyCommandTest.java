package commands;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static stubs.CardStubs.gustavoCard;

@RunWith(MockitoJUnitRunner.class)
public class StartJourneyCommandTest {
    @Mock
    private SwipeCardInCommand swipeCardInCommand;

    @InjectMocks
    private StartJourneyCommand startJourneyCommand;

    @Test
    public void journeyStart_ShouldSwipeCardIn() {
        startJourneyCommand.run(gustavoCard().getUserName());

        verify(swipeCardInCommand).run(gustavoCard().getUserName());
    }
}