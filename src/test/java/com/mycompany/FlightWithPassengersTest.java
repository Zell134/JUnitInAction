package com.mycompany;

import com.mycompany.annotations.FlightNumber;
import com.mycompany.producers.FlightProducer;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class FlightWithPassengersTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClasses(Passenger.class, Flight.class, FlightProducer.class, DistancesManager.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    @FlightNumber(number = "AA1234")
    Flight flight;

    @Inject
    @FlightNumber(number = "AA1235")
    Flight flight2;

    @Inject
    @FlightNumber(number = "AA1236")
    Flight flight3;

    @Inject
    DistancesManager distancesManager;

    private static Map<Passenger, Integer> passengersDistancesMap = new HashMap<>();

    @BeforeClass
    public static void setUp() {
        passengersDistancesMap.put(new Passenger("900-45-6809", "Susan Todd", "GB"), 2100);
        passengersDistancesMap.put(new Passenger("900-45-6797", "Harry Christensen", "GB"), 4200);
        passengersDistancesMap.put(new Passenger("123-45-6799", "Bethany King", "US"), 6300);
    }

    @Test(expected = RuntimeException.class)
    public void testNumberOfSeatsCannotBeExceeded() throws IOException {
        assertEquals(50, flight.getPassengersNumber());
        flight.addPassenger(new Passenger("124-56-7890", "Michael Johnson", "US"));
    }

    @Test
    public void testAddRemovePassengers() throws IOException {
        flight.setSeats(51);
        Passenger additionalPassenger = new Passenger("124-56-7890", "Michael Johnson", "US");
        flight.addPassenger(additionalPassenger);
        assertEquals(51, flight.getPassengersNumber());
        flight.removePassenger(additionalPassenger);
        assertEquals(50, flight.getPassengersNumber());
        assertEquals(51, flight.getSeats());
    }

    @Test
    public void testFlightsDistances() {
        for (Passenger passenger : flight.getPassengers()) {
            distancesManager.addDistance(passenger, flight.getDistance());
        }
        for (Passenger passenger : flight2.getPassengers()) {
            distancesManager.addDistance(passenger, flight2.getDistance());
        }
        for (Passenger passenger : flight3.getPassengers()) {
            distancesManager.addDistance(passenger, flight3.getDistance());
        }
        distancesManager.calculateGivenPoints();
        assertEquals(210, distancesManager.getPassengersPointsMap()
                .get(new Passenger("900-45-6809", "Susan Todd", "GB"))
                .longValue());
        assertEquals(420, distancesManager.getPassengersPointsMap()
                .get(new Passenger("900-45-6797", "Harry Christensen", "GB"))
                .longValue());
        assertEquals(630, distancesManager.getPassengersPointsMap()
                .get(new Passenger("123-45-6799", "Bethany King", "US"))
                .longValue());
    }
}
