package oyster.card.management.commands;

import org.junit.Before;
import org.junit.Test;
import oyster.card.management.models.Station;
import oyster.card.management.models.TransportType;
import oyster.card.management.models.Journey;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static oyster.card.management.models.TransportType.BUS;
import static oyster.card.management.models.TransportType.TRAIN;
import static oyster.card.management.support.FareConstants.anyBusJourney;
import static oyster.card.management.support.FareConstants.anyOneZoneOutsideZoneOne;
import static oyster.card.management.support.FareConstants.anyThreeZones;
import static oyster.card.management.support.FareConstants.anyTwoZonesExcludingZoneOne;
import static oyster.card.management.support.FareConstants.anyTwoZonesIncludingZoneOne;
import static oyster.card.management.support.FareConstants.anywhereInZoneOne;
import static oyster.card.management.support.FareConstants.fares;
import static oyster.card.management.support.StationConstants.earlsCourt;
import static oyster.card.management.support.StationConstants.hammersmith;
import static oyster.card.management.support.StationConstants.holborn;
import static oyster.card.management.support.StationConstants.wimbledon;

public class CalculateJourneyFareCommandTest {
    private CalculateJourneyFareCommand calculateJourneyFareCommand;

    @Before
    public void setUp()
    {
        calculateJourneyFareCommand = new CalculateJourneyFareCommand(fares());
    }

    @Test
    public void tripBetweenHolbornAndEarlsCourtByTrainShouldBe_2_50() {
        BigDecimal fare = calculateJourneyFareCommand.run(buildTripFor(holborn(), earlsCourt(), TRAIN));

        assertThat(fare, is(anywhereInZoneOne().getValue()));
    }

    @Test
    public void tripBetweenEarlsCourtAndHammersmithByTrainShouldBe_2_00() {
        BigDecimal fare = calculateJourneyFareCommand.run(buildTripFor(earlsCourt(), hammersmith(), TRAIN));

        assertThat(fare, is(anyOneZoneOutsideZoneOne().getValue()));
    }

    @Test
    public void tripBetweenHolbornAndHammersmithByTrainShouldBe_3_00() {
        BigDecimal fare = calculateJourneyFareCommand.run(buildTripFor(holborn(), hammersmith(), TRAIN));

        assertThat(fare, is(anyTwoZonesIncludingZoneOne().getValue()));
    }

    @Test
    public void tripBetweenWimbledonAndHammersmithByTrainShouldBe_2_25() {
        BigDecimal fare = calculateJourneyFareCommand.run(buildTripFor(wimbledon(), hammersmith(), TRAIN));

        assertThat(fare, is(anyTwoZonesExcludingZoneOne().getValue()));
    }

    @Test
    public void tripBetweenHolbornAndWimbledomByTrainShouldBe_3_20() {
        BigDecimal fare = calculateJourneyFareCommand.run(buildTripFor(holborn(), wimbledon(), TRAIN));

        assertThat(fare, is(anyThreeZones().getValue()));
    }

    @Test
    public void tripBetweenHolbornAndWimbledonByBusShouldBe_1_80() {
        BigDecimal fare = calculateJourneyFareCommand.run(buildTripFor(holborn(), wimbledon(), BUS));

        assertThat(fare, is(anyBusJourney().getValue()));
    }

    private Journey buildTripFor(Station origin, Station destination, TransportType transportType) {
        return Journey.builder()
                .origin(origin)
                .destination(destination)
                .transportType(transportType)
                .build();
    }
}