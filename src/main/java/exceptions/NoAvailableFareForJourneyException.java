package exceptions;

import models.Journey;

import static java.lang.String.format;

public class NoAvailableFareForJourneyException extends RuntimeException {
    public NoAvailableFareForJourneyException(Journey journey) {
        super(format("There is no available fare from %s to %s by %s.",
                journey.getOrigin().getName(), journey.getDestination().getName(), journey.getTransportType()));
    }
}
