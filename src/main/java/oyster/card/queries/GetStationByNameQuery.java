package oyster.card.queries;

import oyster.card.exceptions.StationNotFoundException;
import oyster.card.models.Station;
import oyster.card.repositories.StationRepository;

public class GetStationByNameQuery {
    private StationRepository stationRepository;

    public GetStationByNameQuery(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    public Station run(String name) {
        return stationRepository
                .get(name)
                .orElseThrow(() -> new StationNotFoundException(name));
    }
}