package models;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class Station {
    private String name;

    @Singular
    private List<Integer> zones;
}
