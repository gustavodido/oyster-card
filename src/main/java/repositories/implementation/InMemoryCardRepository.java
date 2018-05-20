package repositories.implementation;

import models.Card;
import repositories.CardRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Optional.ofNullable;

public class InMemoryCardRepository implements CardRepository {
    private static Map<String, Card> cards = new HashMap<>();

    @Override
    public void save(Card card) {
        cards.put(card.getUserName(), card);
    }

    @Override
    public Optional<Card> get(String userName) {
        return ofNullable(cards.get(userName));
    }
}
