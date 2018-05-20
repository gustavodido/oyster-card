package repositories;

import models.Station;

import java.util.Optional;

public interface StationRepository {
    Optional<Station> get(String name);

}
