package oyster.card.queries;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static oyster.card.support.JourneyFactory.newJourney;
import static oyster.card.support.StationFactory.earlsCourt;
import static oyster.card.support.StationFactory.hammersmith;
import static oyster.card.support.StationFactory.holborn;
import static oyster.card.support.StationFactory.wimbledon;

@RunWith(MockitoJUnitRunner.class)
public class IsZoneOneCrossedQueryTest {
    @InjectMocks
    private IsZoneOneCrossedQuery isZoneOneCrossedQuery;

    @Test
    public void fromEarlsCourtToWimbledon_ShouldNotCrossZoneOne() {
        boolean zoneOneCrossed = isZoneOneCrossedQuery.run(newJourney(earlsCourt(), wimbledon()));

        assertThat(zoneOneCrossed, is(false));
    }

    @Test
    public void fromHolbornToHammersmith_ShouldCrossZoneOne() {
        boolean zoneOneCrossed = isZoneOneCrossedQuery.run(newJourney(holborn(), hammersmith()));

        assertThat(zoneOneCrossed, is(true));
    }

    @Test
    public void fromHammersmithToHolborn_ShouldCrossZoneOne() {
        boolean zoneOneCrossed = isZoneOneCrossedQuery.run(newJourney(hammersmith(), holborn()));

        assertThat(zoneOneCrossed, is(true));
    }

    @Test
    public void fromHammersmithToWimbledon_ShouldNotCrossZoneOne() {
        boolean zoneOneCrossed = isZoneOneCrossedQuery.run(newJourney(hammersmith(), wimbledon()));

        assertThat(zoneOneCrossed, is(false));
    }
}