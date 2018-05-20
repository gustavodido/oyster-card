package commands;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import models.Card;
import repositories.CardRepository;

import java.math.BigDecimal;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static support.CardFactory.gustavoCard;

@RunWith(MockitoJUnitRunner.class)
public class UpdateCardBalanceCommandTest {
    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private UpdateCardBalanceCommand updateCardBalanceCommand;

    @Test
    public void loadAmountInNewCard_ShouldCreateCard() {
        when(cardRepository.get(gustavoCard().getUserName())).thenReturn(empty());

        Card card = updateCardBalanceCommand.run(gustavoCard().getUserName(), gustavoCard().getBalance());

        assertThat(card, is(gustavoCard()));
    }

    @Test
    public void loadAmountInExistingCard_ShouldUpdateBalance() {
        when(cardRepository.get(gustavoCard().getUserName())).thenReturn(of(gustavoCard()));

        Card card = updateCardBalanceCommand.run(gustavoCard().getUserName(), gustavoCard().getBalance());

        BigDecimal expectedBalance = gustavoCard().getBalance().multiply(new BigDecimal(2));

        assertThat(card.getBalance(), is(expectedBalance));
    }

}