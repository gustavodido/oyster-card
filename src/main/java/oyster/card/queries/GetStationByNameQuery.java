package oyster.card.queries;

import oyster.card.exceptions.InvalidStationNameException;
import oyster.card.models.Station;

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
