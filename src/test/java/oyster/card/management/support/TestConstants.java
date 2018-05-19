package oyster.card.management.support;

import oyster.card.management.models.Station;

public class TestConstants {
    public static Station holbornStation() {
        return Station.builder().name("Holborn").build();
    }
}
