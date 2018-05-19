package oyster.card.management.commands;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
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
import static oyster.card.management.support.TripFactory.newJourney;

public class CalculateJourneyFareCommandTest {
    private CalculateJourneyFareCommand calculateJourneyFareCommand;

    @Before
    public void setUp()
    {
        calculateJourneyFareCommand = new CalculateJourneyFareCommand(fares());
    }

    @Test
    public void tripBetweenHolbornAndEarlsCourtByTrainShouldBe_2_50() {
        BigDecimal fare = calculateJourneyFareCommand.run(newJourney(holborn(), earlsCourt(), TRAIN));

        assertThat(fare, is(anywhereInZoneOne().getValue()));
    }

    @Test
    public void tripBetweenEarlsCourtAndHammersmithByTrainShouldBe_2_00() {
        BigDecimal fare = calculateJourneyFareCommand.run(newJourney(earlsCourt(), hammersmith(), TRAIN));

        assertThat(fare, is(anyOneZoneOutsideZoneOne().getValue()));
    }

    @Test
    public void tripBetweenHolbornAndHammersmithByTrainShouldBe_3_00() {
        BigDecimal fare = calculateJourneyFareCommand.run(newJourney(holborn(), hammersmith(), TRAIN));

        assertThat(fare, is(anyTwoZonesIncludingZoneOne().getValue()));
    }

    @Test
    public void tripBetweenWimbledonAndHammersmithByTrainShouldBe_2_25() {
        BigDecimal fare = calculateJourneyFareCommand.run(newJourney(wimbledon(), hammersmith(), TRAIN));

        assertThat(fare, is(anyTwoZonesExcludingZoneOne().getValue()));
    }

    @Test
    public void tripBetweenHolbornAndWimbledomByTrainShouldBe_3_20() {
        BigDecimal fare = calculateJourneyFareCommand.run(newJourney(holborn(), wimbledon(), TRAIN));

        assertThat(fare, is(anyThreeZones().getValue()));
    }

    @Test
    public void tripBetweenHolbornAndWimbledonByBusShouldBe_1_80() {
        BigDecimal fare = calculateJourneyFareCommand.run(newJourney(holborn(), wimbledon(), BUS));

        assertThat(fare, is(anyBusJourney().getValue()));
    }
}