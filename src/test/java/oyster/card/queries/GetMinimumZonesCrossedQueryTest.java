package oyster.card.queries;

import org.junit.Before;
import org.junit.Test;
import oyster.card.support.JourneyFactory;
import oyster.card.support.StationFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static oyster.card.support.JourneyFactory.newJourney;

public class GetMinimumZonesCrossedQueryTest {
    private GetMinimumZonesCrossedQuery getMinimumZonesCrossedQuery;

    @Before
    public void setUp() {
        getMinimumZonesCrossedQuery = new GetMinimumZonesCrossedQuery();
    }

    @Test
    public void sameZoneJourney_ShouldBeOneZone() {
        int zonesCrossed = getMinimumZonesCrossedQuery.run(JourneyFactory.newJourney(StationFactory.earlsCourt(), StationFactory.earlsCourt()));

        assertThat(zonesCrossed, is(1));
    }

    @Test
    public void multipleOptionsJourney_ShouldBeTheMinimumPossible() {
        int zonesCrossed = getMinimumZonesCrossedQuery.run(JourneyFactory.newJourney(StationFactory.holborn(), StationFactory.earlsCourt()));

        assertThat(zonesCrossed, is(1));
    }

    @Test
    public void fromHolbornToWimbledon_ShouldBeAllThreeZones() {
        int zonesCrossed = getMinimumZonesCrossedQuery.run(JourneyFactory.newJourney(StationFactory.holborn(), StationFactory.wimbledon()));

        assertThat(zonesCrossed, is(3));
    }

    @Test
    public void fromEarlsCourtToWimbledon_ShouldBeTwoZones() {
        int zonesCrossed = getMinimumZonesCrossedQuery.run(JourneyFactory.newJourney(StationFactory.earlsCourt(), StationFactory.wimbledon()));

        assertThat(zonesCrossed, is(2));
    }
}