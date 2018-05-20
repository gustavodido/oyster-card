package queries;

import exceptions.CardNotFoundException;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import repositories.CardRepository;
import support.CardFactory;

import java.math.BigDecimal;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetCardBalanceByUserNameQueryTest {
    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private GetCardBalanceByUserNameQuery getCardBalanceByUserNameQuery;

    @Test
    public void queryByUserName_ShouldReturnCardBalance() {
        when(cardRepository.get(CardFactory.gustavoCard().getUserName())).thenReturn(of(CardFactory.gustavoCard()));

        BigDecimal expectedBalance = getCardBalanceByUserNameQuery.run(CardFactory.gustavoCard().getUserName());
        assertThat(expectedBalance, CoreMatchers.is(CardFactory.gustavoCard().getBalance()));
    }

    @Test(expected = CardNotFoundException.class)
    public void nonExistentUserName_ShouldThrowException() {
        when(cardRepository.get(CardFactory.invalidCard().getUserName())).thenReturn(empty());

        getCardBalanceByUserNameQuery.run(CardFactory.invalidCard().getUserName());
    }

}