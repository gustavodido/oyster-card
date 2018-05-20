package oyster.card.queries;

import oyster.card.exceptions.InvalidStationNameException;
import oyster.card.models.Station;
import oyster.card.repositories.StationRepository;

public class GetStationByNameQuery {
    private StationRepository stationRepository;

    public GetStationByNameQuery(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    public Station run(String name) {
        return stationRepository
                .getByName(name)
                .orElseThrow(() -> new InvalidStationNameException(name));
    }
}