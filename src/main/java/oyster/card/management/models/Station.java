package oyster.card.management.models;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class Station {
    private String name;
    private List<Integer> zones;
}
