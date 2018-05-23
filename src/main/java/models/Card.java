package models;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder(toBuilder = true)
public class Card {
    private String userName;
    private BigDecimal balance;
    private boolean isSwipedIn;
}
