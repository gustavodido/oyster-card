package oyster.card.management.commands;

import oyster.card.management.models.Trip;

import java.util.List;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Math.abs;
import static java.lang.Math.min;

class CalculateMinimumZonesCrossedCommand {
    int run(Trip trip) {
        List<Integer> originZones = trip.getOrigin().getZones();
        List<Integer> destinationZones = trip.getDestination().getZones();

        int minimumZonesCrossed = MAX_VALUE;
        for (int fromZone : originZones) {
            for (int toZone: destinationZones) {
                int zonesCrossed = abs(fromZone - toZone) + 1;
                minimumZonesCrossed = min(minimumZonesCrossed, zonesCrossed);
            }
        }

        return minimumZonesCrossed;
    }
}
