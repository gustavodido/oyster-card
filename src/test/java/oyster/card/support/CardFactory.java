package oyster.card.support;

import oyster.card.models.Card;

import static java.math.BigDecimal.TEN;

public class CardFactory {
    public static Card gustavoCard() {
        return Card.builder()
                .userName("Gustavo")
                .balance(TEN)
                .build();
    }
}
