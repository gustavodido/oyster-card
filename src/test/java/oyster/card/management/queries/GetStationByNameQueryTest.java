package oyster.card.management.queries;

import org.junit.Before;
import org.junit.Test;
import oyster.card.management.exceptions.InvalidStationNameException;
import oyster.card.management.models.Station;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static oyster.card.management.support.StationConstants.holborn;

public class GetStationByNameQueryTest {
    private GetStationByNameQuery getStationByNameQuery;

    @Before
    public void setUp() {
        getStationByNameQuery = new GetStationByNameQuery(singletonList(holborn()));
    }
    @Test
    public void shouldReturnStationByName() {
        Station expectedStation = getStationByNameQuery.run(holborn().getName());
        assertThat(expectedStation, is(holborn()));
    }

    @Test(expected = InvalidStationNameException.class)
    public void shouldRaiseExceptionIfStationDoesNotExists() {
        getStationByNameQuery.run("Random station name...");
    }
}