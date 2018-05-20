package oyster.card.repositories;

import oyster.card.models.Fare;

import java.util.List;

public interface FareRepository {
    List<Fare> list();
}
