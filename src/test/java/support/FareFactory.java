package support;

import models.Fare;
import models.TransportType;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Arrays.asList;

public class FareFactory {
    public static Fare anywhereInZoneOne() {
        return Fare.builder()
                .transportType(TransportType.TRAIN)
                .zonesCrossed(1)
                .zoneOneIncluded(true)
                .value(new BigDecimal(2.50)).build();
    }

    public static Fare anyOneZoneOutsideZoneOne() {
        return Fare.builder()
                .transportType(TransportType.TRAIN)
                .zonesCrossed(1)
                .zoneOneIncluded(false)
                .value(new BigDecimal(2.00)).build();
    }

    public static Fare anyTwoZonesIncludingZoneOne() {
        return Fare.builder()
                .transportType(TransportType.TRAIN)
                .zonesCrossed(2)
                .zoneOneIncluded(true)
                .value(new BigDecimal(3.00))
                .build();
    }

    public static Fare anyTwoZonesExcludingZoneOne() {
        return Fare.builder()
                .transportType(TransportType.TRAIN)
                .zonesCrossed(2)
                .zoneOneIncluded(false)
                .value(new BigDecimal(2.25))
                .build();
    }

    public static Fare anyThreeZones() {
        return Fare.builder()
                .transportType(TransportType.TRAIN)
                .zonesCrossed(3)
                .zoneOneIncluded(true)
                .value(new BigDecimal(3.20))
                .build();
    }

    public static Fare anyBusJourney() {
        return Fare.builder()
                .transportType(TransportType.BUS)
                .zoneOneIncluded(true)
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
