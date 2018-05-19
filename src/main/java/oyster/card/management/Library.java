package oyster.card.management;

import oyster.card.management.model.Station;

public class Library {
    public boolean someLibraryMethod() {
        Station s = new Station("Temp");
        return s.getName().equals("Temp");
    }
}
