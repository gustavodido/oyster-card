package oyster.card.management.support;

import oyster.card.management.models.Station;

import java.util.List;

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

    public static List<Station> stations() {
        return asList(holborn(), earlsCourt(), wimbledon(), hammersmith());
    }
}
