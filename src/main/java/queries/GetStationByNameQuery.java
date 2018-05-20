package queries;

import exceptions.StationNotFoundException;
import repositories.StationRepository;
import models.Station;

public class GetStationByNameQuery {
    private final StationRepository stationRepository;

    public GetStationByNameQuery(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    public Station run(String name) {
        return stationRepository
                .get(name)
                .orElseThrow(() -> new StationNotFoundException(name));
    }
}