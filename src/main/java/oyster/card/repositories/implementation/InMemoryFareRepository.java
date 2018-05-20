package oyster.card.repositories.implementation;

import oyster.card.models.Fare;
import oyster.card.models.TransportType;
import oyster.card.repositories.FareRepository;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Arrays.asList;

public class InMemoryFareRepository implements FareRepository {
    private static List<Fare> fares = asList(
            Fare.builder()
                    .transportType(TransportType.TRAIN)
                    .zonesCrossed(1)
                    .zoneOneIncluded(true)
                    .value(new BigDecimal(2.50)).build(),
            Fare.builder()
                    .transportType(TransportType.TRAIN)
                    .zonesCrossed(1)
                    .zoneOneIncluded(false)
                    .value(new BigDecimal(2.00)).build(),
            Fare.builder()
                    .transportType(TransportType.TRAIN)
                    .zonesCrossed(2)
                    .zoneOneIncluded(true)
                    .value(new BigDecimal(3.00))
                    .build(),
            Fare.builder()
                    .transportType(TransportType.TRAIN)
                    .zonesCrossed(2)
                    .zoneOneIncluded(false)
                    .value(new BigDecimal(2.25))
                    .build(),
            Fare.builder()
                    .transportType(TransportType.TRAIN)
                    .zonesCrossed(3)
                    .zoneOneIncluded(true)
                    .value(new BigDecimal(3.20))
                    .build(),
            Fare.builder()
                    .transportType(TransportType.BUS)
                    .zoneOneIncluded(true)
                    .value(new BigDecimal(1.80))
                    .build()
    );

    @Override
    public List<Fare> list() {
        return fares;
    }
}
