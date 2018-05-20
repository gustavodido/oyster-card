package oyster.card.repositories;

import oyster.card.models.Card;

public interface CardRepository {
    void save(Card card);
    Card get(String userName);
}
