package commands;

import exceptions.NoAvailableFareForJourneyException;
import models.Journey;
import models.TransportType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import queries.GetMinimumZonesCrossedQuery;
import queries.IsZoneOneCrossedQuery;
import repositories.FareRepository;
import stubs.FareStubs;
import stubs.JourneyStubs;
import stubs.StationStubs;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

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
        when(fareRepository.list()).thenReturn(FareStubs.fares());
    }

    @Test
    public void fromHolbornToEarlsCourtByTrain_ShouldBeInZoneOneFare() {
        BigDecimal actualFare = calculateJourneyFareForCrossedZones(JourneyStubs.newJourney(StationStubs.holborn(), StationStubs.earlsCourt(), TransportType.TRAIN), 1, true);

        assertThat(actualFare, is(FareStubs.anywhereInZoneOne().getValue()));
    }

    @Test
    public void fromEarlsCourtToHammersmithByTrain_ShouldBeOneZoneExcludingZoneOneFare() {
        BigDecimal actualFare = calculateJourneyFareForCrossedZones(JourneyStubs.newJourney(StationStubs.earlsCourt(), StationStubs.hammersmith(), TransportType.TRAIN), 1, false);

        assertThat(actualFare, is(FareStubs.anyOneZoneOutsideZoneOne().getValue()));
    }

    @Test
    public void fromHolbornToHammersmithByTrain_ShouldBeTwoZonesFare() {
        BigDecimal actualFare = calculateJourneyFareForCrossedZones(JourneyStubs.newJourney(StationStubs.holborn(), StationStubs.hammersmith(), TransportType.TRAIN), 2, true);

        assertThat(actualFare, is(FareStubs.anyTwoZonesIncludingZoneOne().getValue()));
    }

    @Test
    public void fromWimbledonToHammersmithByTrain_ShouldBeTwoZonesExcludingZoneOneFare() {
        BigDecimal actualFare = calculateJourneyFareForCrossedZones(JourneyStubs.newJourney(StationStubs.wimbledon(), StationStubs.hammersmith(), TransportType.TRAIN), 2, false);

        assertThat(actualFare, is(FareStubs.anyTwoZonesExcludingZoneOne().getValue()));
    }

    @Test
    public void fromHolbornToWimbledomByTrain_ShouldBeThreeZonesFare() {
        BigDecimal actualFare = calculateJourneyFareForCrossedZones(JourneyStubs.newJourney(StationStubs.holborn(), StationStubs.wimbledon(), TransportType.TRAIN), 3, true);

        assertThat(actualFare, is(FareStubs.anyThreeZones().getValue()));
    }

    @Test
    public void fromHolbornToWimbledonByBus_ShouldBeBusJourneyFare() {
        BigDecimal actualFare = calculateJourneyFareCommand.run(JourneyStubs.newJourney(StationStubs.holborn(), StationStubs.wimbledon(), TransportType.BUS));

        assertThat(actualFare, is(FareStubs.anyBusJourney().getValue()));
    }

    @Test
    public void journeyBetweenBusStations_ShouldBeBusJourneyFare() {
        BigDecimal actualFare = calculateJourneyFareCommand.run(JourneyStubs.newJourney(StationStubs.chelsea(), StationStubs.chelsea(), TransportType.BUS));

        assertThat(actualFare, is(FareStubs.anyBusJourney().getValue()));
    }

    @Test(expected = NoAvailableFareForJourneyException.class)
    public void journeyWithoutAvailableFare_ShouldThrowException() {
        calculateJourneyFareForCrossedZones(JourneyStubs.newJourney(StationStubs.chelsea(), StationStubs.chelsea(), TransportType.TRAIN), 4, true);
    }

    private BigDecimal calculateJourneyFareForCrossedZones(Journey journey, int minimumCrossedZones, boolean isZoneOneCrossed) {
        when(getMinimumZonesCrossedQuery.run(journey)).thenReturn(minimumCrossedZones);
        when(isZoneOneCrossedQuery.run(journey)).thenReturn(isZoneOneCrossed);

        return calculateJourneyFareCommand.run(journey);
    }
}