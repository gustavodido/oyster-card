package queries;

import exceptions.CardNotFoundException;
import models.Card;
import repositories.CardRepository;

public class GetCardByUserNameQuery {
    private final CardRepository cardRepository;

    public GetCardByUserNameQuery(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public Card run(String userName) {
        return cardRepository.get(userName)
                .orElseThrow(() -> new CardNotFoundException(userName));
    }
}