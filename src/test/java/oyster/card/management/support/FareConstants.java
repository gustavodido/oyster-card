package oyster.card.management.support;

import oyster.card.management.models.Fare;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static oyster.card.management.models.TransportType.BUS;
import static oyster.card.management.models.TransportType.TRAIN;

public class FareConstants {
    public static Fare anywhereInZoneOne() {
        return Fare.builder()
                .transportType(TRAIN)
                .zonesCrossed(1)
                .value(new BigDecimal(2.50)).build();
    }

    public static Fare anyOneZoneOutsideZoneOne() {
        return Fare.builder()
                .transportType(TRAIN)
                .zonesCrossed(1)
                .excludingZones(singletonList(1))
                .value(new BigDecimal(2.00)).build();
    }

    public static Fare anyTwoZonesIncludingZoneOne() {
        return Fare.builder()
                .transportType(TRAIN)
                .zonesCrossed(2)
                .value(new BigDecimal(3.00))
                .build();
    }

    public static Fare anyTwoZonesExcludingZoneOne() {
        return Fare.builder()
                .transportType(TRAIN)
                .zonesCrossed(2)
                .excludingZones(singletonList(1))
                .value(new BigDecimal(2.25))
                .build();
    }

    public static Fare anyThreeZones() {
        return Fare.builder()
                .transportType(TRAIN)
                .zonesCrossed(3)
                .value(new BigDecimal(3.20))
                .build();
    }

    public static Fare anyBusJourney() {
        return Fare.builder()
                .transportType(BUS)
                .value(new BigDecimal(1.80))
                .build();
    }

    public static List<Fare> fares() {
        return asList(
                anywhereInZoneOne(),
                anyOneZoneOutsideZoneOne(),
                anyTwoZonesIncludingZoneOne(),
                anyTwoZonesExcludingZoneOne(),
                anyThreeZones(),
                anyBusJourney()
        );
    }
}
