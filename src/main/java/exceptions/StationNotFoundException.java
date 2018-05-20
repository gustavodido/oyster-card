package exceptions;

import static java.lang.String.format;

public class StationNotFoundException extends RuntimeException {
    public StationNotFoundException(String name) {
        super(format("%s is not a valid station name.", name));
    }
}
