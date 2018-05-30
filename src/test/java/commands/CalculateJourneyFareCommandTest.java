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

import java.math.BigDecimal;

import static models.TransportType.TRAIN;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static stubs.FareStubs.anyBusJourney;
import static stubs.FareStubs.anyOneZoneOutsideZoneOne;
import static stubs.FareStubs.anyThreeZones;
import static stubs.FareStubs.anyTwoZonesExcludingZoneOne;
import static stubs.FareStubs.anyTwoZonesIncludingZoneOne;
import static stubs.FareStubs.anywhereInZoneOne;
import static stubs.FareStubs.fares;
import static stubs.JourneyStubs.newJourney;
import static stubs.StationStubs.chelsea;
import static stubs.StationStubs.earlsCourt;
import static stubs.StationStubs.hammersmith;
import static stubs.StationStubs.holborn;
import static stubs.StationStubs.wimbledon;

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
        when(fareRepository.list()).thenReturn(fares());
    }

    @Test
    public void fromHolbornToEarlsCourtByTrain_ShouldBeInZoneOneFare() {
        BigDecimal actualFare = calculateJourneyFareForCrossedZones(newJourney(holborn(), earlsCourt(), TRAIN), 1, true);

        assertThat(actualFare, is(anywhereInZoneOne().getValue()));
    }

    @Test
    public void fromEarlsCourtToHammersmithByTrain_ShouldBeOneZoneExcludingZoneOneFare() {
        BigDecimal actualFare = calculateJourneyFareForCrossedZones(newJourney(earlsCourt(), hammersmith(), TRAIN), 1, false);

        assertThat(actualFare, is(anyOneZoneOutsideZoneOne().getValue()));
    }

    @Test
    public void fromHolbornToHammersmithByTrain_ShouldBeTwoZonesFare() {
        BigDecimal actualFare = calculateJourneyFareForCrossedZones(newJourney(holborn(), hammersmith(), TRAIN), 2, true);

        assertThat(actualFare, is(anyTwoZonesIncludingZoneOne().getValue()));
    }

    @Test
    public void fromWimbledonToHammersmithByTrain_ShouldBeTwoZonesExcludingZoneOneFare() {
        BigDecimal actualFare = calculateJourneyFareForCrossedZones(newJourney(wimbledon(), hammersmith(), TRAIN), 2, false);

        assertThat(actualFare, is(anyTwoZonesExcludingZoneOne().getValue()));
    }

    @Test
    public void fromHolbornToWimbledomByTrain_ShouldBeThreeZonesFare() {
        BigDecimal actualFare = calculateJourneyFareForCrossedZones(newJourney(holborn(), wimbledon(), TRAIN), 3, true);

        assertThat(actualFare, is(anyThreeZones().getValue()));
    }

    @Test
    public void fromHolbornToWimbledonByBus_ShouldBeBusJourneyFare() {
        BigDecimal actualFare = calculateJourneyFareCommand.run(newJourney(holborn(), wimbledon(), TransportType.BUS));

        assertThat(actualFare, is(anyBusJourney().getValue()));
    }

    @Test
    public void journeyBetweenBusStations_ShouldBeBusJourneyFare() {
        BigDecimal actualFare = calculateJourneyFareCommand.run(newJourney(chelsea(), chelsea(), TransportType.BUS));

        assertThat(actualFare, is(anyBusJourney().getValue()));
    }

    @Test(expected = NoAvailableFareForJourneyException.class)
    public void journeyWithoutAvailableFare_ShouldThrowException() {
        calculateJourneyFareForCrossedZones(newJourney(chelsea(), chelsea(), TRAIN), 4, true);
    }

    private BigDecimal calculateJourneyFareForCrossedZones(Journey journey, int minimumCrossedZones, boolean isZoneOneCrossed) {
        when(getMinimumZonesCrossedQuery.run(journey)).thenReturn(minimumCrossedZones);
        when(isZoneOneCrossedQuery.run(journey)).thenReturn(isZoneOneCrossed);

        return calculateJourneyFareCommand.run(journey);
    }
}