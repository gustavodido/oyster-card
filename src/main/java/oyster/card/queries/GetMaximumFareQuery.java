package oyster.card.queries;

import oyster.card.models.Fare;
import oyster.card.repositories.FareRepository;

import static java.util.Comparator.comparing;

public class GetMaximumFareQuery {
    private final FareRepository fareRepository;

    public GetMaximumFareQuery(FareRepository fareRepository) {
        this.fareRepository = fareRepository;
    }

    public Fare run() {
        return fareRepository.list()
                .stream()
                .max(comparing(Fare::getValue))
                .orElse(null);
    }
}