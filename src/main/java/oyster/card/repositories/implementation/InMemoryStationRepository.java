package oyster.card.repositories.implementation;

import oyster.card.models.Station;
import oyster.card.repositories.StationRepository;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

public class InMemoryStationRepository implements StationRepository {
    @Override
    public List<Station> list() {
        return asList(
                Station.builder().name("Holborn").zones(singletonList(1)).build(),
                Station.builder().name("Earl’s Court").zones(asList(1, 2)).build(),
                Station.builder().name("Wimbledon").zones(singletonList(3)).build(),
                Station.builder().name("Hammersmith").zones(singletonList(2)).build(),
                Station.builder().name("Chelsea").build());
    }
}
