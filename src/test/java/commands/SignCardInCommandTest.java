package commands;

import exceptions.CardNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import repositories.CardRepository;

import static java.util.Optional.of;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static stubs.CardStubs.gustavoCard;
import static stubs.CardStubs.invalidCard;
import static stubs.CardStubs.signedInGustavoCard;

@RunWith(MockitoJUnitRunner.class)
public class SignCardInCommandTest {
    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private SignCardInCommand signCardInCommand;

    @Test
    public void signIn_ShouldUpdateTheCard() {
        when(cardRepository.get(gustavoCard().getUserName())).thenReturn(of(gustavoCard()));

        signCardInCommand.run(gustavoCard().getUserName());

        verify(cardRepository).save(signedInGustavoCard());
    }

    @Test(expected = CardNotFoundException.class)
    public void signInNonexistentCard_ShouldThrowAnException() {
        signCardInCommand.run(invalidCard().getUserName());
    }

}