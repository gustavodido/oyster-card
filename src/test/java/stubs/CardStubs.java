package stubs;

import models.Card;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;

public class CardStubs {
    public static Card gustavoCard() {
        return Card.builder()
                .userName("Gustavo")
                .balance(TEN)
                .isSignedIn(false)
                .build();
    }

    public static Card signedInGustavoCard() {
        return  gustavoCard().toBuilder()
                .isSignedIn(true)
                .build();
    }

    public static Card invalidCard() {
        return Card.builder()
                .userName("Invalid card")
                .build();
    }

    public static Card notFundedCard() {
        return gustavoCard()
                .toBuilder()
                .balance(ONE)
                .build();
    }

}
