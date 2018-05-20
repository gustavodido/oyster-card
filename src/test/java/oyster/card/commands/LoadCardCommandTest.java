package oyster.card.commands;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import oyster.card.models.Card;
import oyster.card.repositories.CardRepository;

import java.math.BigDecimal;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static oyster.card.support.CardFactory.gustavoCard;

@RunWith(MockitoJUnitRunner.class)
public class LoadCardCommandTest {
    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private LoadCardCommand loadCardCommand;

    @Test
    public void loadAmountInNewCard_ShouldCreateCard() {
        when(cardRepository.get(gustavoCard().getUserName())).thenReturn(null);

        Card card = loadCardCommand.run(gustavoCard().getUserName(), gustavoCard().getBalance());

        assertThat(card, is(gustavoCard()));
    }

    @Test
    public void loadAmountInExistingCard_ShouldUpdateBalance() {
        when(cardRepository.get(gustavoCard().getUserName())).thenReturn(gustavoCard());

        Card card = loadCardCommand.run(gustavoCard().getUserName(), gustavoCard().getBalance());

        BigDecimal expectedBalance = gustavoCard().getBalance().multiply(new BigDecimal(2));

        assertThat(card.getBalance(), is(expectedBalance));
    }

}