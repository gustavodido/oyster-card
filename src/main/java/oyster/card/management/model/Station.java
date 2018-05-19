package oyster.card.management.model;

import lombok.Value;

import java.util.List;

@Value
public class Station {
    private String name;
    private List<Integer> zones;
}
