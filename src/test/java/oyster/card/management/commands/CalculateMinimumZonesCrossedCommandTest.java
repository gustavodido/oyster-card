package oyster.card.management.commands;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static oyster.card.management.support.StationFactory.earlsCourt;
import static oyster.card.management.support.StationFactory.holborn;
import static oyster.card.management.support.StationFactory.wimbledon;
import static oyster.card.management.support.TripFactory.newJourney;

public class CalculateMinimumZonesCrossedCommandTest {
    private CalculateMinimumZonesCrossedCommand calculateMinimumZonesCrossedCommand;

    @Before
    public void setUp() {
        calculateMinimumZonesCrossedCommand = new CalculateMinimumZonesCrossedCommand();
    }

    @Test
    public void shouldReturnOneForSameZoneJourneys() {
        int zonesCrossed = calculateMinimumZonesCrossedCommand.run(newJourney(earlsCourt(), earlsCourt()));

        assertThat(zonesCrossed, is(1));
    }

    @Test
    public void shouldReturnMinimumZonesForMultipleOptionsJourneys() {
        int zonesCrossed = calculateMinimumZonesCrossedCommand.run(newJourney(holborn(), earlsCourt()));

        assertThat(zonesCrossed, is(1));
    }

    @Test
    public void shouldReturnThreeZonesFromHolbornToWimbledon() {
        int zonesCrossed = calculateMinimumZonesCrossedCommand.run(newJourney(holborn(), wimbledon()));

        assertThat(zonesCrossed, is(3));
    }

    @Test
    public void shouldReturnTwoZonesFromEarlsCourtToWimbledon() {
        int zonesCrossed = calculateMinimumZonesCrossedCommand.run(newJourney(earlsCourt(), wimbledon()));

        assertThat(zonesCrossed, is(2));
    }
}