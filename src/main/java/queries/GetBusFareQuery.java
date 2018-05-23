package queries;

import models.Fare;
import repositories.FareRepository;

import static models.TransportType.BUS;

public class GetBusFareQuery {
    private final FareRepository fareRepository;

    public GetBusFareQuery(FareRepository fareRepository) {
        this.fareRepository = fareRepository;
    }

    public Fare run() {
        return fareRepository.list()
                .stream()
                .filter(f -> f.getTransportType() == BUS)
                .findFirst()
                .orElse(null);
    }
}