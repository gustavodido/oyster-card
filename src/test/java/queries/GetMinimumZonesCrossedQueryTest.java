package queries;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import stubs.JourneyStubs;
import stubs.StationStubs;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class GetMinimumZonesCrossedQueryTest {
    @InjectMocks
    private GetMinimumZonesCrossedQuery getMinimumZonesCrossedQuery;

    @Test
    public void sameZoneJourney_ShouldBeOneZone() {
        int zonesCrossed = getMinimumZonesCrossedQuery.run(JourneyStubs.newJourney(StationStubs.earlsCourt(), StationStubs.earlsCourt()));

        assertThat(zonesCrossed, is(1));
    }

    @Test
    public void multipleOptionsJourney_ShouldBeTheMinimumPossible() {
        int zonesCrossed = getMinimumZonesCrossedQuery.run(JourneyStubs.newJourney(StationStubs.holborn(), StationStubs.earlsCourt()));

        assertThat(zonesCrossed, is(1));
    }

    @Test
    public void fromHolbornToWimbledon_ShouldBeAllThreeZones() {
        int zonesCrossed = getMinimumZonesCrossedQuery.run(JourneyStubs.newJourney(StationStubs.holborn(), StationStubs.wimbledon()));

        assertThat(zonesCrossed, is(3));
    }

    @Test
    public void fromEarlsCourtToWimbledon_ShouldBeTwoZones() {
        int zonesCrossed = getMinimumZonesCrossedQuery.run(JourneyStubs.newJourney(StationStubs.earlsCourt(), StationStubs.wimbledon()));

        assertThat(zonesCrossed, is(2));
    }
}