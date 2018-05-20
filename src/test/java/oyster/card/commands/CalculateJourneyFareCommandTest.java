package oyster.card.commands;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import oyster.card.exceptions.NoAvailableFareForJourneyException;
import oyster.card.models.Journey;
import oyster.card.models.TransportType;
import oyster.card.queries.GetMinimumZonesCrossedQuery;
import oyster.card.queries.IsZoneOneCrossedQuery;
import oyster.card.repositories.FareRepository;
import oyster.card.support.FareFactory;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static oyster.card.support.FareFactory.fares;
import static oyster.card.support.JourneyFactory.newJourney;
import static oyster.card.support.StationFactory.chelsea;
import static oyster.card.support.StationFactory.earlsCourt;
import static oyster.card.support.StationFactory.hammersmith;
import static oyster.card.support.StationFactory.holborn;
import static oyster.card.support.StationFactory.wimbledon;

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
        BigDecimal actualFare = calculateJourneyFareForCrossedZones(newJourney(holborn(), earlsCourt(), TransportType.TRAIN), 1, true);

        assertThat(actualFare, is(FareFactory.anywhereInZoneOne().getValue()));
    }

    @Test
    public void fromEarlsCourtToHammersmithByTrain_ShouldBeOneZoneExcludingZoneOneFare() {
        BigDecimal actualFare = calculateJourneyFareForCrossedZones(newJourney(earlsCourt(), hammersmith(), TransportType.TRAIN), 1, false);

        assertThat(actualFare, is(FareFactory.anyOneZoneOutsideZoneOne().getValue()));
    }

    @Test
    public void fromHolbornToHammersmithByTrain_ShouldBeTwoZonesFare() {
        BigDecimal actualFare = calculateJourneyFareForCrossedZones(newJourney(holborn(), hammersmith(), TransportType.TRAIN), 2, true);

        assertThat(actualFare, is(FareFactory.anyTwoZonesIncludingZoneOne().getValue()));
    }

    @Test
    public void fromWimbledonToHammersmithByTrain_ShouldBeTwoZonesExcludingZoneOneFare() {
        BigDecimal actualFare = calculateJourneyFareForCrossedZones(newJourney(wimbledon(), hammersmith(), TransportType.TRAIN), 2, false);

        assertThat(actualFare, is(FareFactory.anyTwoZonesExcludingZoneOne().getValue()));
    }

    @Test
    public void fromHolbornToWimbledomByTrain_ShouldBeThreeZonesFare() {
        BigDecimal actualFare = calculateJourneyFareForCrossedZones(newJourney(holborn(), wimbledon(), TransportType.TRAIN), 3, true);

        assertThat(actualFare, is(FareFactory.anyThreeZones().getValue()));
    }

    @Test
    public void fromHolbornToWimbledonByBus_ShouldBeBusJourneyFare() {
        BigDecimal actualFare = calculateJourneyFareCommand.run(newJourney(holborn(), wimbledon(), TransportType.BUS));

        assertThat(actualFare, is(FareFactory.anyBusJourney().getValue()));
    }

    @Test
    public void journeyBetweenBusStations_ShouldBeBusJourneyFare() {
        BigDecimal actualFare = calculateJourneyFareCommand.run(newJourney(chelsea(), chelsea(), TransportType.BUS));

        assertThat(actualFare, is(FareFactory.anyBusJourney().getValue()));
    }

    @Test(expected = NoAvailableFareForJourneyException.class)
    public void journeyWithoutAvailableFare_ShouldThrowException() {
        calculateJourneyFareForCrossedZones(newJourney(chelsea(), chelsea(), TransportType.TRAIN), 4, true);
    }

    private BigDecimal calculateJourneyFareForCrossedZones(Journey journey, int minimumCrossedZones, boolean isZoneOneCrossed) {
        when(getMinimumZonesCrossedQuery.run(journey)).thenReturn(minimumCrossedZones);
        when(isZoneOneCrossedQuery.run(journey)).thenReturn(isZoneOneCrossed);

        return calculateJourneyFareCommand.run(journey);
    }
}