package oyster.card.commands;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import oyster.card.models.Journey;
import oyster.card.queries.GetMinimumZonesCrossedQuery;
import oyster.card.models.TransportType;
import oyster.card.support.FareFactory;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static oyster.card.support.StationFactory.earlsCourt;
import static oyster.card.support.StationFactory.hammersmith;
import static oyster.card.support.StationFactory.holborn;
import static oyster.card.support.StationFactory.wimbledon;
import static oyster.card.support.JourneyFactory.newJourney;

@RunWith(MockitoJUnitRunner.class)
public class CalculateJourneyFareCommandTest {
    @Mock
    private GetMinimumZonesCrossedQuery getMinimumZonesCrossedQuery;

    private CalculateJourneyFareCommand calculateJourneyFareCommand;

    @Before
    public void setUp() {
        calculateJourneyFareCommand = new CalculateJourneyFareCommand(FareFactory.fares(), getMinimumZonesCrossedQuery);
    }

    @Test
    public void fromHolbornToEarlsCourtByTrain_ShouldBeInZoneOne() {
        BigDecimal actualFare = calculateJourneyFareForCrossedZones(newJourney(holborn(), earlsCourt(), TransportType.TRAIN), 1);

        assertThat(actualFare, is(FareFactory.anywhereInZoneOne().getValue()));
    }

    @Test
    public void fromEarlsCourtToHammersmithByTrain_ShouldBeOneZoneExcludingZoneOne() {
        BigDecimal actualFare = calculateJourneyFareForCrossedZones(newJourney(earlsCourt(), hammersmith(), TransportType.TRAIN), 1);

        assertThat(actualFare, is(FareFactory.anyOneZoneOutsideZoneOne().getValue()));
    }

    @Test
    public void fromHolbornToHammersmithByTrain_ShouldBeTwoZones() {
        BigDecimal actualFare = calculateJourneyFareForCrossedZones(newJourney(holborn(), hammersmith(), TransportType.TRAIN), 2);

        assertThat(actualFare, is(FareFactory.anyTwoZonesIncludingZoneOne().getValue()));
    }

    @Test
    public void fromWimbledonToHammersmithByTrain_ShouldBeTwoZonesExcludingZoneOne() {
        BigDecimal actualFare = calculateJourneyFareForCrossedZones(newJourney(wimbledon(), hammersmith(), TransportType.TRAIN), 2);

        assertThat(actualFare, is(FareFactory.anyTwoZonesExcludingZoneOne().getValue()));
    }

    @Test
    public void fromHolbornToWimbledomByTrain_ShouldBeThreeZones() {
        BigDecimal actualFare = calculateJourneyFareForCrossedZones(newJourney(holborn(), wimbledon(), TransportType.TRAIN), 3);

        assertThat(actualFare, is(FareFactory.anyThreeZones().getValue()));
    }

    @Test
    public void fromHolbornToWimbledonByBus_ShouldBeBusJourney() {
        BigDecimal actualFare = calculateJourneyFareForCrossedZones(newJourney(holborn(), wimbledon(), TransportType.BUS), 3);

        assertThat(actualFare, is(FareFactory.anyBusJourney().getValue()));
    }

    private BigDecimal calculateJourneyFareForCrossedZones(Journey journey, int minimumCrossedZones) {
        when(getMinimumZonesCrossedQuery.run(journey)).thenReturn(minimumCrossedZones);

        return calculateJourneyFareCommand.run(journey);
    }
}