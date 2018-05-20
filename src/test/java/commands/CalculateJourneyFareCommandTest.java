package commands;

import exceptions.NoAvailableFareForJourneyException;
import models.Journey;
import repositories.FareRepository;
import support.FareFactory;
import support.JourneyFactory;
import support.StationFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import models.TransportType;
import queries.GetMinimumZonesCrossedQuery;
import queries.IsZoneOneCrossedQuery;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static support.JourneyFactory.newJourney;

@RunWith(MockitoJUnitRunner.class)
public class CalculateJourneyFareCommandTest {
    @Mock
    private FareRepository fareRepository;

    @Mock
    private GetMinimumZonesCrossedQuery getMinimumZonesCrossedQuery;

    @Mock
    private IsZoneOneCrossedQuery isZoneOneCrossedQuery;

    @InjectMocks
    private CalculateJourneyFareCommand calculateJourneyFareCommand;

    @Before
    public void setUp() {
        when(fareRepository.list()).thenReturn(FareFactory.fares());
    }

    @Test
    public void fromHolbornToEarlsCourtByTrain_ShouldBeInZoneOneFare() {
        BigDecimal actualFare = calculateJourneyFareForCrossedZones(JourneyFactory.newJourney(StationFactory.holborn(), StationFactory.earlsCourt(), TransportType.TRAIN), 1, true);

        assertThat(actualFare, is(FareFactory.anywhereInZoneOne().getValue()));
    }

    @Test
    public void fromEarlsCourtToHammersmithByTrain_ShouldBeOneZoneExcludingZoneOneFare() {
        BigDecimal actualFare = calculateJourneyFareForCrossedZones(JourneyFactory.newJourney(StationFactory.earlsCourt(), StationFactory.hammersmith(), TransportType.TRAIN), 1, false);

        assertThat(actualFare, is(FareFactory.anyOneZoneOutsideZoneOne().getValue()));
    }

    @Test
    public void fromHolbornToHammersmithByTrain_ShouldBeTwoZonesFare() {
        BigDecimal actualFare = calculateJourneyFareForCrossedZones(JourneyFactory.newJourney(StationFactory.holborn(), StationFactory.hammersmith(), TransportType.TRAIN), 2, true);

        assertThat(actualFare, is(FareFactory.anyTwoZonesIncludingZoneOne().getValue()));
    }

    @Test
    public void fromWimbledonToHammersmithByTrain_ShouldBeTwoZonesExcludingZoneOneFare() {
        BigDecimal actualFare = calculateJourneyFareForCrossedZones(JourneyFactory.newJourney(StationFactory.wimbledon(), StationFactory.hammersmith(), TransportType.TRAIN), 2, false);

        assertThat(actualFare, is(FareFactory.anyTwoZonesExcludingZoneOne().getValue()));
    }

    @Test
    public void fromHolbornToWimbledomByTrain_ShouldBeThreeZonesFare() {
        BigDecimal actualFare = calculateJourneyFareForCrossedZones(JourneyFactory.newJourney(StationFactory.holborn(), StationFactory.wimbledon(), TransportType.TRAIN), 3, true);

        assertThat(actualFare, is(FareFactory.anyThreeZones().getValue()));
    }

    @Test
    public void fromHolbornToWimbledonByBus_ShouldBeBusJourneyFare() {
        BigDecimal actualFare = calculateJourneyFareCommand.run(JourneyFactory.newJourney(StationFactory.holborn(), StationFactory.wimbledon(), TransportType.BUS));

        assertThat(actualFare, is(FareFactory.anyBusJourney().getValue()));
    }

    @Test
    public void journeyBetweenBusStations_ShouldBeBusJourneyFare() {
        BigDecimal actualFare = calculateJourneyFareCommand.run(JourneyFactory.newJourney(StationFactory.chelsea(), StationFactory.chelsea(), TransportType.BUS));

        assertThat(actualFare, is(FareFactory.anyBusJourney().getValue()));
    }

    @Test(expected = NoAvailableFareForJourneyException.class)
    public void journeyWithoutAvailableFare_ShouldThrowException() {
        calculateJourneyFareForCrossedZones(JourneyFactory.newJourney(StationFactory.chelsea(), StationFactory.chelsea(), TransportType.TRAIN), 4, true);
    }

    private BigDecimal calculateJourneyFareForCrossedZones(Journey journey, int minimumCrossedZones, boolean isZoneOneCrossed) {
        when(getMinimumZonesCrossedQuery.run(journey)).thenReturn(minimumCrossedZones);
        when(isZoneOneCrossedQuery.run(journey)).thenReturn(isZoneOneCrossed);

        return calculateJourneyFareCommand.run(journey);
    }
}