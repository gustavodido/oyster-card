package exceptions;

import java.math.BigDecimal;

import static java.lang.String.format;

public class InvalidCardBalanceException extends RuntimeException {
    public InvalidCardBalanceException(BigDecimal newCardBalance) {
        super(format("Card balance(%s) cannot be less than zero.", newCardBalance));
    }
}
