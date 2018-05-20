package queries;

import exceptions.StationNotFoundException;
import repositories.StationRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import models.Station;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static support.StationFactory.holborn;
import static support.StationFactory.invalidStation;

@RunWith(MockitoJUnitRunner.class)
public class GetStationByNameQueryTest {
    @Mock
    private StationRepository stationRepository;

    @InjectMocks
    private GetStationByNameQuery getStationByNameQuery;

    @Test
    public void queryByName_ShouldReturnStation() {
        when(stationRepository.get(holborn().getName())).thenReturn(of(holborn()));

        Station expectedStation = getStationByNameQuery.run(holborn().getName());
        assertThat(expectedStation, is(holborn()));
    }

    @Test(expected = StationNotFoundException.class)
    public void nonExistentName_ShouldThrowException() {
        when(stationRepository.get(invalidStation().getName())).thenReturn(empty());

        getStationByNameQuery.run(invalidStation().getName());
    }
}