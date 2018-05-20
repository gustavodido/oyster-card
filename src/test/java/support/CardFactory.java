package support;

import models.Card;

import static java.math.BigDecimal.TEN;

public class CardFactory {
    public static Card gustavoCard() {
        return Card.builder()
                .userName("Gustavo")
                .balance(TEN)
                .build();
    }

    public static Card invalidCard() {
        return Card.builder()
                .userName("Invalid card")
                .build();
    }

}
