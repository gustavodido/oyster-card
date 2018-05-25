package commands;

import exceptions.CardNotFoundException;
import models.Card;
import repositories.CardRepository;

public class SignCardInCommand {
    private final CardRepository cardRepository;

    public SignCardInCommand(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public void run(String userName) {
        Card card = cardRepository.get(userName)
                .orElseThrow(() -> new CardNotFoundException(userName));

        Card updatedCard = card.toBuilder()
                .isSignedIn(true)
                .build();

        cardRepository.save(updatedCard);
    }
}
