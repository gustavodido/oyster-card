package queries;

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
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static stubs.CardStubs.gustavoCard;
import static stubs.CardStubs.invalidCard;

@RunWith(MockitoJUnitRunner.class)
public class GetCardByUserNameQueryTest {
    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private GetCardByUserNameQuery getCardByUserNameQuery;

    @Test
    public void queryByUserName_ShouldReturnCardBalance() {
        when(cardRepository.get(gustavoCard().getUserName())).thenReturn(of(gustavoCard()));

        Card expectedCard = getCardByUserNameQuery.run(gustavoCard().getUserName());
        assertThat(expectedCard, is(gustavoCard()));
    }

    @Test(expected = CardNotFoundException.class)
    public void nonExistentUserName_ShouldThrowException() {
        when(cardRepository.get(invalidCard().getUserName())).thenReturn(empty());

        getCardByUserNameQuery.run(invalidCard().getUserName());
    }

}