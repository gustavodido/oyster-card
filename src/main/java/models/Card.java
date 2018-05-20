package models;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class Card {
    private String userName;
    private BigDecimal balance;
}
