package queries;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import stubs.JourneyStubs;
import stubs.StationStubs;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class IsZoneOneCrossedQueryTest {
    @InjectMocks
    private IsZoneOneCrossedQuery isZoneOneCrossedQuery;

    @Test
    public void fromEarlsCourtToWimbledon_ShouldNotCrossZoneOne() {
        boolean zoneOneCrossed = isZoneOneCrossedQuery.run(JourneyStubs.newJourney(StationStubs.earlsCourt(), StationStubs.wimbledon()));

        assertThat(zoneOneCrossed, is(false));
    }

    @Test
    public void fromHolbornToHammersmith_ShouldCrossZoneOne() {
        boolean zoneOneCrossed = isZoneOneCrossedQuery.run(JourneyStubs.newJourney(StationStubs.holborn(), StationStubs.hammersmith()));

        assertThat(zoneOneCrossed, is(true));
    }

    @Test
    public void fromHammersmithToHolborn_ShouldCrossZoneOne() {
        boolean zoneOneCrossed = isZoneOneCrossedQuery.run(JourneyStubs.newJourney(StationStubs.hammersmith(), StationStubs.holborn()));

        assertThat(zoneOneCrossed, is(true));
    }

    @Test
    public void fromHammersmithToWimbledon_ShouldNotCrossZoneOne() {
        boolean zoneOneCrossed = isZoneOneCrossedQuery.run(JourneyStubs.newJourney(StationStubs.hammersmith(), StationStubs.wimbledon()));

        assertThat(zoneOneCrossed, is(false));
    }
}