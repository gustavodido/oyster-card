package oyster.card.management.queries;

import oyster.card.management.exceptions.InvalidStationNameException;
import oyster.card.management.models.Station;

import java.util.List;

public class GetStationByNameQuery {
    private List<Station> stations;

    public GetStationByNameQuery(List<Station> stations) {
        this.stations = stations;
    }

    public Station run(String name) {
        return stations
                .stream()
                .filter(s -> s.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new InvalidStationNameException(name));
    }
}
