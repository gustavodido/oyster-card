package stubs;

import models.Card;

import static java.math.BigDecimal.TEN;

public class CardStubs {
    public static Card gustavoCard() {
        return Card.builder()
                .userName("Gustavo")
                .balance(TEN)
                .isSwipedIn(false)
                .build();
    }

    public static Card gustavoSwipedInCard() {
        return gustavoCard().toBuilder()
                .isSwipedIn(true)
                .build();
    }

    public static Card invalidCard() {
        return Card.builder()
                .userName("Invalid card")
                .build();
    }

}
