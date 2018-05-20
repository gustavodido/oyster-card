package oyster.card.commands;

import oyster.card.models.Card;
import oyster.card.repositories.CardRepository;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;

public class UpdateCardBalanceCommand {
    private final CardRepository cardRepository;

    public UpdateCardBalanceCommand(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public Card run(String userName, BigDecimal amount) {
        BigDecimal balance = cardRepository.get(userName)
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
