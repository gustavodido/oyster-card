package commands;

import repositories.CardRepository;
import models.Card;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;

public class UpdateCardBalanceCommand {
    private final CardRepository cardRepository;

    public UpdateCardBalanceCommand(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public Card run(String userName, BigDecimal amount) {
        Card currentCard = cardRepository.get(userName)
                .orElse(Card.builder()
                        .userName(userName)
                        .balance(ZERO)
                        .build());

        Card card = currentCard.toBuilder()
                .balance(currentCard.getBalance().add(amount))
                .build();

        cardRepository.save(card);
        return card;
    }
}
