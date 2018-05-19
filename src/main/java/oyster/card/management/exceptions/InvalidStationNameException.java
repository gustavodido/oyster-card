package oyster.card.management.exceptions;

import static java.lang.String.format;

public class InvalidStationNameException extends RuntimeException {
    public InvalidStationNameException(String name) {
        super(format("%s is not a valid station name.", name));
    }
}
