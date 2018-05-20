package repositories.implementation;

import repositories.StationRepository;
import models.Station;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

public class InMemoryStationRepository implements StationRepository {
    private static List<Station> stations =
            asList(
                    Station.builder().name("Holborn").zones(singletonList(1)).build(),
                    Station.builder().name("Earl's Court").zones(asList(1, 2)).build(),
                    Station.builder().name("Wimbledon").zones(singletonList(3)).build(),
                    Station.builder().name("Hammersmith").zones(singletonList(2)).build(),
                    Station.builder().name("Chelsea").build());

    @Override
    public Optional<Station> get(String name) {
        return stations
                .stream()
                .filter(s -> s.getName().equals(name))
                .findFirst();
    }
}
