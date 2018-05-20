package exceptions;

import static java.lang.String.format;

public class CardNotFoundException extends RuntimeException {
    public CardNotFoundException(String userName) {
        super(format("No card found for user %s.", userName));
    }
}
