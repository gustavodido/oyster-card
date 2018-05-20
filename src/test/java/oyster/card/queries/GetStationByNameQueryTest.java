package oyster.card.queries;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import oyster.card.exceptions.InvalidStationNameException;
import oyster.card.models.Station;
import oyster.card.repositories.StationRepository;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static oyster.card.support.StationFactory.holborn;
import static oyster.card.support.StationFactory.invalidStation;

@RunWith(MockitoJUnitRunner.class)
public class GetStationByNameQueryTest {
    @Mock
    private StationRepository stationRepository;

    @InjectMocks
    private GetStationByNameQuery getStationByNameQuery;

    @Test
    public void queryByName_ShouldReturnStation() {
        when(stationRepository.getByName(holborn().getName())).thenReturn(of(holborn()));

        Station expectedStation = getStationByNameQuery.run(holborn().getName());
        assertThat(expectedStation, is(holborn()));
    }

    @Test(expected = InvalidStationNameException.class)
    public void nonExistentName_ShouldThrowException() {
        when(stationRepository.getByName(invalidStation().getName())).thenReturn(empty());

        getStationByNameQuery.run(invalidStation().getName());
    }
}