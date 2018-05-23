package commands;

import exceptions.CardNotFoundException;
import models.Card;
import repositories.CardRepository;

public class SwipeCardInCommand {
    private final CardRepository cardRepository;

    public SwipeCardInCommand(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public Card run(String userName) {
        Card currentCard = cardRepository.get(userName)
                .orElseThrow(() -> new CardNotFoundException(userName));

        Card card = currentCard.toBuilder()
                .isSwipedIn(true)
                .build();

        cardRepository.save(card);
        return card;
    }
}
