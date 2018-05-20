package queries;

import exceptions.CardNotFoundException;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import repositories.CardRepository;
import stubs.CardStubs;

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
        when(cardRepository.get(CardStubs.gustavoCard().getUserName())).thenReturn(of(CardStubs.gustavoCard()));

        BigDecimal expectedBalance = getCardBalanceByUserNameQuery.run(CardStubs.gustavoCard().getUserName());
        assertThat(expectedBalance, CoreMatchers.is(CardStubs.gustavoCard().getBalance()));
    }

    @Test(expected = CardNotFoundException.class)
    public void nonExistentUserName_ShouldThrowException() {
        when(cardRepository.get(CardStubs.invalidCard().getUserName())).thenReturn(empty());

        getCardBalanceByUserNameQuery.run(CardStubs.invalidCard().getUserName());
    }

}