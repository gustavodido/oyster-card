package exceptions;

import java.math.BigDecimal;

import static java.lang.String.format;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(String userName, BigDecimal currentBalance) {
        super(format("The user %s does not have sufficient funds (%s).", userName, currentBalance));
    }
}
