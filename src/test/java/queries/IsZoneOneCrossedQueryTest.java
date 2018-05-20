package queries;

import support.JourneyFactory;
import support.StationFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static support.JourneyFactory.newJourney;

@RunWith(MockitoJUnitRunner.class)
public class IsZoneOneCrossedQueryTest {
    @InjectMocks
    private IsZoneOneCrossedQuery isZoneOneCrossedQuery;

    @Test
    public void fromEarlsCourtToWimbledon_ShouldNotCrossZoneOne() {
        boolean zoneOneCrossed = isZoneOneCrossedQuery.run(JourneyFactory.newJourney(StationFactory.earlsCourt(), StationFactory.wimbledon()));

        assertThat(zoneOneCrossed, is(false));
    }

    @Test
    public void fromHolbornToHammersmith_ShouldCrossZoneOne() {
        boolean zoneOneCrossed = isZoneOneCrossedQuery.run(JourneyFactory.newJourney(StationFactory.holborn(), StationFactory.hammersmith()));

        assertThat(zoneOneCrossed, is(true));
    }

    @Test
    public void fromHammersmithToHolborn_ShouldCrossZoneOne() {
        boolean zoneOneCrossed = isZoneOneCrossedQuery.run(JourneyFactory.newJourney(StationFactory.hammersmith(), StationFactory.holborn()));

        assertThat(zoneOneCrossed, is(true));
    }

    @Test
    public void fromHammersmithToWimbledon_ShouldNotCrossZoneOne() {
        boolean zoneOneCrossed = isZoneOneCrossedQuery.run(JourneyFactory.newJourney(StationFactory.hammersmith(), StationFactory.wimbledon()));

        assertThat(zoneOneCrossed, is(false));
    }
}