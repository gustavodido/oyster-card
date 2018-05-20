package queries;

import exceptions.CardNotFoundException;
import repositories.CardRepository;

import java.math.BigDecimal;

public class GetCardBalanceByUserNameQuery {
    private final CardRepository cardRepository;

    public GetCardBalanceByUserNameQuery(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public BigDecimal run(String userName) {
        return cardRepository.get(userName)
                .orElseThrow(() -> new CardNotFoundException(userName))
                .getBalance();
    }
}