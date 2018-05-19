package oyster.card.queries;

import org.junit.Before;
import org.junit.Test;
import oyster.card.exceptions.InvalidStationNameException;
import oyster.card.models.Station;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static oyster.card.support.StationFactory.holborn;

public class GetStationByNameQueryTest {
    private GetStationByNameQuery getStationByNameQuery;

    @Before
    public void setUp() {
        getStationByNameQuery = new GetStationByNameQuery(singletonList(holborn()));
    }
    @Test
    public void queryByName_ShouldReturnStation() {
        Station expectedStation = getStationByNameQuery.run(holborn().getName());
        assertThat(expectedStation, is(holborn()));
    }

    @Test(expected = InvalidStationNameException.class)
    public void nonExistentName_ShouldThrowException() {
        getStationByNameQuery.run("Random station name...");
    }
}