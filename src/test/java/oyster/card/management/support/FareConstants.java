package oyster.card.management.support;

import oyster.card.management.models.Fare;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Arrays.asList;
import static oyster.card.management.models.TransportType.BUS;
import static oyster.card.management.models.TransportType.TRAIN;

public class FareConstants {
    public static Fare anywhereInZoneOne() {
        return Fare.builder()
                .transportType(TRAIN)
                .zonesCrossed(1)
                .canIncludeZoneOne(true)
                .value(new BigDecimal(2.50)).build();
    }

    public static Fare anyOneZoneOutsideZoneOne() {
        return Fare.builder()
                .transportType(TRAIN)
                .zonesCrossed(1)
                .canIncludeZoneOne(false)
                .value(new BigDecimal(2.00)).build();
    }

    public static Fare anyTwoZonesIncludingZoneOne() {
        return Fare.builder()
                .transportType(TRAIN)
                .zonesCrossed(2)
                .canIncludeZoneOne(true)
                .value(new BigDecimal(3.00))
                .build();
    }

    public static Fare anyTwoZonesExcludingZoneOne() {
        return Fare.builder()
                .transportType(TRAIN)
                .zonesCrossed(2)
                .canIncludeZoneOne(false)
                .value(new BigDecimal(2.25))
                .build();
    }

    public static Fare anyThreeZones() {
        return Fare.builder()
                .transportType(TRAIN)
                .zonesCrossed(3)
                .canIncludeZoneOne(true)
                .value(new BigDecimal(3.20))
                .build();
    }

    public static Fare anyBusJourney() {
        return Fare.builder()
                .transportType(BUS)
                .canIncludeZoneOne(true)
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
