package queries;

import models.Journey;

import java.util.List;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Math.abs;
import static java.lang.Math.min;

public class GetMinimumZonesCrossedQuery {
    public int run(Journey journey) {
        List<Integer> originZones = journey.getOrigin().getZones();
        List<Integer> destinationZones = journey.getDestination().getZones();

        int minimumZonesCrossed = MAX_VALUE;
        for (int fromZone : originZones) {
            for (int toZone: destinationZones) {
                int zonesCrossed = abs(fromZone - toZone) + 1;
                minimumZonesCrossed = min(minimumZonesCrossed, zonesCrossed);

                // Break the nested loops, one zone is the best scenario
                if (minimumZonesCrossed == 1) {
                    return 1;
                }
            }
        }

        return minimumZonesCrossed;
    }
}
