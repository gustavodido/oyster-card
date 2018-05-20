package oyster.card.repositories.implementation;

import oyster.card.models.Card;
import oyster.card.repositories.CardRepository;

import java.util.HashMap;
import java.util.Map;

public class InMemoryCardRepository implements CardRepository {
    private static Map<String, Card> cards = new HashMap<>();

    @Override
    public void save(Card card) {
        cards.put(card.getUserName(), card);
    }

    @Override
    public Card get(String userName) {
        return cards.get(userName);
    }
}
