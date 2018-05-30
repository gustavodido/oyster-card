package queries;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static stubs.JourneyStubs.newJourney;
import static stubs.StationStubs.earlsCourt;
import static stubs.StationStubs.holborn;
import static stubs.StationStubs.wimbledon;

@RunWith(MockitoJUnitRunner.class)
public class GetMinimumZonesCrossedQueryTest {
    @InjectMocks
    private GetMinimumZonesCrossedQuery getMinimumZonesCrossedQuery;

    @Test
    public void sameZoneJourney_ShouldBeOneZone() {
        int zonesCrossed = getMinimumZonesCrossedQuery.run(newJourney(earlsCourt(), earlsCourt()));

        assertThat(zonesCrossed, is(1));
    }

    @Test
    public void multipleOptionsJourney_ShouldBeTheMinimumPossible() {
        int zonesCrossed = getMinimumZonesCrossedQuery.run(newJourney(holborn(), earlsCourt()));

        assertThat(zonesCrossed, is(1));
    }

    @Test
    public void fromHolbornToWimbledon_ShouldBeAllThreeZones() {
        int zonesCrossed = getMinimumZonesCrossedQuery.run(newJourney(holborn(), wimbledon()));

        assertThat(zonesCrossed, is(3));
    }

    @Test
    public void fromEarlsCourtToWimbledon_ShouldBeTwoZones() {
        int zonesCrossed = getMinimumZonesCrossedQuery.run(newJourney(earlsCourt(), wimbledon()));

        assertThat(zonesCrossed, is(2));
    }
}