package commands;

import exceptions.CardNotFoundException;
import models.Card;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import repositories.CardRepository;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static stubs.CardStubs.gustavoCard;

@RunWith(MockitoJUnitRunner.class)
public class SwipeCardInCommandTest {
    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private SwipeCardInCommand swipeCardInCommand;

    @Test
    public void  swipeIn_ShouldUpdateUserCard() {
        when(cardRepository.get(gustavoCard().getUserName())).thenReturn(of(gustavoCard()));

        Card updatedCard = swipeCardInCommand.run(gustavoCard().getUserName());
        assertThat(updatedCard.isSwipedIn(), is(true));
    }

    @Test(expected = CardNotFoundException.class)
    public void  swipeInNonexistentCard_ShouldThrowException() {
        when(cardRepository.get(gustavoCard().getUserName())).thenReturn(empty());

        swipeCardInCommand.run(gustavoCard().getUserName());
    }
}