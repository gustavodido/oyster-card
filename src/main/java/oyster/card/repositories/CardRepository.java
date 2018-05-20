package oyster.card.repositories;

import oyster.card.models.Card;

import java.util.Optional;

public interface CardRepository {
    void save(Card card);
    Optional<Card> get(String userName);
}
