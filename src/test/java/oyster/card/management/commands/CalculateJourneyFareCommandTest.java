package oyster.card.management.commands;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import oyster.card.management.models.Journey;
import oyster.card.management.queries.GetMinimumZonesCrossedQuery;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static oyster.card.management.models.TransportType.BUS;
import static oyster.card.management.models.TransportType.TRAIN;
import static oyster.card.management.support.FareFactory.anyBusJourney;
import static oyster.card.management.support.FareFactory.anyOneZoneOutsideZoneOne;
import static oyster.card.management.support.FareFactory.anyThreeZones;
import static oyster.card.management.support.FareFactory.anyTwoZonesExcludingZoneOne;
import static oyster.card.management.support.FareFactory.anyTwoZonesIncludingZoneOne;
import static oyster.card.management.support.FareFactory.anywhereInZoneOne;
import static oyster.card.management.support.FareFactory.fares;
import static oyster.card.management.support.StationFactory.earlsCourt;
import static oyster.card.management.support.StationFactory.hammersmith;
import static oyster.card.management.support.StationFactory.holborn;
import static oyster.card.management.support.StationFactory.wimbledon;
import static oyster.card.management.support.JourneyFactory.newJourney;

@RunWith(MockitoJUnitRunner.class)
public class CalculateJourneyFareCommandTest {
    @Mock
    private GetMinimumZonesCrossedQuery getMinimumZonesCrossedQuery;

    private CalculateJourneyFareCommand calculateJourneyFareCommand;

    @Before
    public void setUp() {
        calculateJourneyFareCommand = new CalculateJourneyFareCommand(fares(), getMinimumZonesCrossedQuery);
    }

    @Test
    public void fromHolbornToEarlsCourtByTrain_ShouldBeInZoneOne() {
        BigDecimal actualFare = calculateJourneyFareForCrossedZones(newJourney(holborn(), earlsCourt(), TRAIN), 1);

        assertThat(actualFare, is(anywhereInZoneOne().getValue()));
    }

    @Test
    public void fromEarlsCourtToHammersmithByTrain_ShouldBeOneZoneExcludingZoneOne() {
        BigDecimal actualFare = calculateJourneyFareForCrossedZones(newJourney(earlsCourt(), hammersmith(), TRAIN), 1);

        assertThat(actualFare, is(anyOneZoneOutsideZoneOne().getValue()));
    }

    @Test
    public void fromHolbornToHammersmithByTrain_ShouldBeTwoZones() {
        BigDecimal actualFare = calculateJourneyFareForCrossedZones(newJourney(holborn(), hammersmith(), TRAIN), 2);

        assertThat(actualFare, is(anyTwoZonesIncludingZoneOne().getValue()));
    }

    @Test
    public void fromWimbledonToHammersmithByTrain_ShouldBeTwoZonesExcludingZoneOne() {
        BigDecimal actualFare = calculateJourneyFareForCrossedZones(newJourney(wimbledon(), hammersmith(), TRAIN), 2);

        assertThat(actualFare, is(anyTwoZonesExcludingZoneOne().getValue()));
    }

    @Test
    public void fromHolbornToWimbledomByTrain_ShouldBeThreeZones() {
        BigDecimal actualFare = calculateJourneyFareForCrossedZones(newJourney(holborn(), wimbledon(), TRAIN), 3);

        assertThat(actualFare, is(anyThreeZones().getValue()));
    }

    @Test
    public void fromHolbornToWimbledonByBus_ShouldBeBusJourney() {
        BigDecimal actualFare = calculateJourneyFareForCrossedZones(newJourney(holborn(), wimbledon(), BUS), 3);

        assertThat(actualFare, is(anyBusJourney().getValue()));
    }

    private BigDecimal calculateJourneyFareForCrossedZones(Journey journey, int minimumCrossedZones) {
        when(getMinimumZonesCrossedQuery.run(journey)).thenReturn(minimumCrossedZones);

        return calculateJourneyFareCommand.run(journey);
    }
}