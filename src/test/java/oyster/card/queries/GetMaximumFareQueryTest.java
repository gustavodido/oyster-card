package oyster.card.queries;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import oyster.card.models.Fare;
import oyster.card.repositories.FareRepository;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static oyster.card.support.FareFactory.anyBusJourney;
import static oyster.card.support.FareFactory.anyThreeZones;
import static oyster.card.support.FareFactory.anywhereInZoneOne;

@RunWith(MockitoJUnitRunner.class)
public class GetMaximumFareQueryTest {
    @Mock
    private FareRepository fareRepository;

    @InjectMocks
    private GetMaximumFareQuery getMaximumFareQuery;

    @Test
    public void theMaximumFare_ShouldReturnedFromFares() {
        when(fareRepository.list()).thenReturn(asList(anyBusJourney(), anyThreeZones(), anywhereInZoneOne()));

        Fare value = getMaximumFareQuery.run();

        assertThat(value, is(anyThreeZones()));
    }
}