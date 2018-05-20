package oyster.card.support;

import oyster.card.models.Station;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

public class StationFactory {
    public static Station holborn() {
        return Station.builder().name("Holborn").zones(singletonList(1)).build();
    }

    public static Station earlsCourt() {
        return Station.builder().name("Earl’s Court").zones(asList(1, 2)).build();
    }

    public static Station wimbledon() {
        return Station.builder().name("Wimbledon").zones(singletonList(3)).build();
    }

    public static Station hammersmith() {
        return Station.builder().name("Hammersmith").zones(singletonList(2)).build();
    }

    public static Station chelsea() {
        return Station.builder().name("Chelsea").build();
    }

    public static Station invalidStation() {
        return Station.builder().name("Invalid Station").zones(singletonList(4)).build();
    }

}
