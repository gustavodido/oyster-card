package queries;

import exceptions.CardNotFoundException;
import models.Card;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import repositories.CardRepository;
import stubs.CardStubs;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetCardByUserNameQueryTest {
    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private GetCardByUserNameQuery getCardByUserNameQuery;

    @Test
    public void queryByUserName_ShouldReturnCardBalance() {
        when(cardRepository.get(CardStubs.gustavoCard().getUserName())).thenReturn(of(CardStubs.gustavoCard()));

        Card card = getCardByUserNameQuery.run(CardStubs.gustavoCard().getUserName());
        assertThat(card.getBalance(), is(CardStubs.gustavoCard().getBalance()));
    }

    @Test(expected = CardNotFoundException.class)
    public void nonExistentUserName_ShouldThrowException() {
        when(cardRepository.get(CardStubs.invalidCard().getUserName())).thenReturn(empty());

        getCardByUserNameQuery.run(CardStubs.invalidCard().getUserName());
    }

}