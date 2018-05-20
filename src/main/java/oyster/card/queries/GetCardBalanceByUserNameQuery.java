package oyster.card.queries;

import oyster.card.exceptions.CardNotFoundException;
import oyster.card.repositories.CardRepository;

import java.math.BigDecimal;

public class GetCardBalanceByUserNameQuery {
    private CardRepository cardRepository;

    public GetCardBalanceByUserNameQuery(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public BigDecimal run(String userName) {
        return cardRepository.get(userName)
                .orElseThrow(() -> new CardNotFoundException(userName))
                .getBalance();
    }
}