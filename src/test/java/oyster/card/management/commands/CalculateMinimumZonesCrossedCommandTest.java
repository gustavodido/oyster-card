package oyster.card.management.commands;

import org.junit.Before;
import org.junit.Test;
import oyster.card.management.models.Trip;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static oyster.card.management.support.StationConstants.earlsCourt;
import static oyster.card.management.support.StationConstants.holborn;
import static oyster.card.management.support.StationConstants.wimbledon;

public class CalculateMinimumZonesCrossedCommandTest {
    private CalculateMinimumZonesCrossedCommand calculateMinimumZonesCrossedCommand;

    @Before
    public void setUp() {
        calculateMinimumZonesCrossedCommand = new CalculateMinimumZonesCrossedCommand();
    }

    @Test
    public void shouldReturnOneForSameZoneTrips() {
        int zonesCrossed = calculateMinimumZonesCrossedCommand.run(Trip.builder()
                .origin(earlsCourt())
                .destination(earlsCourt())
                .build());

        assertThat(zonesCrossed, is(1));
    }

    @Test
    public void shouldReturnMinimumZonesForMultipleOptionsTrips() {
        int zonesCrossed = calculateMinimumZonesCrossedCommand.run(Trip.builder()
                .origin(holborn())
                .destination(earlsCourt())
                .build());

        assertThat(zonesCrossed, is(1));
    }

    @Test
    public void shouldReturnThreeZonesFromHolbornToWimbledon() {
        int zonesCrossed = calculateMinimumZonesCrossedCommand.run(Trip.builder()
                .origin(holborn())
                .destination(wimbledon())
                .build());

        assertThat(zonesCrossed, is(3));
    }


    @Test
    public void shouldReturnTwoZonesFromEarlsCourtToWimbledon() {
        int zonesCrossed = calculateMinimumZonesCrossedCommand.run(Trip.builder()
                .origin(earlsCourt())
                .destination(wimbledon())
                .build());

        assertThat(zonesCrossed, is(2));
    }
}