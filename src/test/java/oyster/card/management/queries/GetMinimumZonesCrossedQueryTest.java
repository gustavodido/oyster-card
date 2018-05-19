package oyster.card.management.queries;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static oyster.card.management.support.StationFactory.earlsCourt;
import static oyster.card.management.support.StationFactory.holborn;
import static oyster.card.management.support.StationFactory.wimbledon;
import static oyster.card.management.support.TripFactory.newJourney;

public class GetMinimumZonesCrossedQueryTest {
    private GetMinimumZonesCrossedQuery getMinimumZonesCrossedQuery;

    @Before
    public void setUp() {
        getMinimumZonesCrossedQuery = new GetMinimumZonesCrossedQuery();
    }

    @Test
    public void shouldReturnOneForSameZoneJourneys() {
        int zonesCrossed = getMinimumZonesCrossedQuery.run(newJourney(earlsCourt(), earlsCourt()));

        assertThat(zonesCrossed, is(1));
    }

    @Test
    public void shouldReturnMinimumZonesForMultipleOptionsJourneys() {
        int zonesCrossed = getMinimumZonesCrossedQuery.run(newJourney(holborn(), earlsCourt()));

        assertThat(zonesCrossed, is(1));
    }

    @Test
    public void shouldReturnThreeZonesFromHolbornToWimbledon() {
        int zonesCrossed = getMinimumZonesCrossedQuery.run(newJourney(holborn(), wimbledon()));

        assertThat(zonesCrossed, is(3));
    }

    @Test
    public void shouldReturnTwoZonesFromEarlsCourtToWimbledon() {
        int zonesCrossed = getMinimumZonesCrossedQuery.run(newJourney(earlsCourt(), wimbledon()));

        assertThat(zonesCrossed, is(2));
    }
}