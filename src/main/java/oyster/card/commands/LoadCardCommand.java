package oyster.card.commands;

import oyster.card.models.Card;
import oyster.card.repositories.CardRepository;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;
import static java.util.Optional.ofNullable;

public class LoadCardCommand {
    private final CardRepository cardRepository;

    public LoadCardCommand(CardRepository cardRepository) {

        this.cardRepository = cardRepository;
    }

    public Card run(String userName, BigDecimal amount) {
        BigDecimal balance = ofNullable(cardRepository.get(userName))
                .orElse(Card.builder().balance(ZERO).build())
                .getBalance()
                .add(amount);

        Card card = Card.builder()
                .userName(userName)
                .balance(balance)
                .build();

        cardRepository.save(card);

        return card;
    }
}
