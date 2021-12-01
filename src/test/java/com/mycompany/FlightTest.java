package com.mycompany;

import java.io.IOException;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FlightTest {
    
    Passenger passenger  = new Passenger("123-45-6789", "John Smith", "US");

    @Test
    public void testFlightCreation() {
        Flight flight = new Flight("AA123", 100);
        assertNotNull(flight);
    }

    @Test
    public void testInvalidFlightNumber() {
        assertThrows(RuntimeException.class,
                () -> {
                    Flight flight = new Flight("AA12", 100);
                });
        assertThrows(RuntimeException.class,
                () -> {
                    Flight flight = new Flight("AA12345", 100);
                });
    }

    @Test
    public void testValidFlightNumber() {
        Flight flight = new Flight("AA345", 100);
        assertNotNull(flight);
        flight = new Flight("AA3456", 100);
        assertNotNull(flight);
    }

    @Test
    public void testAddPassengers() throws IOException {
        Flight flight = FlightBuilderUtil.buildFlightFromCsv("AA1234", 50,"src/test/resources/flights_information.csv");
        assertEquals(50, flight.getPassengersNumber());
        assertThrows(RuntimeException.class,
                () -> {
                    flight.addPassenger(passenger);
                });
    }

    @Test
    public void testSetInvalidSeats() throws IOException {
        Flight flight = FlightBuilderUtil.buildFlightFromCsv("AA1234", 50,"src/test/resources/flights_information.csv");
        assertEquals(50, flight.getPassengersNumber());
        assertThrows(RuntimeException.class,
                () -> {
                    flight.setSeats(49);
                });
    }

    @Test
    public void testSetValidSeats() throws IOException {
        Flight flight = FlightBuilderUtil.buildFlightFromCsv("AA1234", 50,"src/test/resources/flights_information.csv");
        assertEquals(50, flight.getPassengersNumber());
        flight.setSeats(52);
        assertEquals(52, flight.getSeats());
    }

    @Test
    public void testChangeOrigin() {
        Flight flight = new Flight("AA1234", 50);
        flight.setOrigin("London");
        flight.setDestination("Bucharest");
        flight.takeOff();
        assertEquals(true, flight.isFlying());
        assertEquals(true, flight.isTakenOff());
        assertEquals(false, flight.isLanded());
        assertThrows(RuntimeException.class,
                () -> {
                    flight.setOrigin("Manchester");
                });
    }

    @Test
    public void testChangeDestination() {
        Flight flight = new Flight("AA1234", 50);
        flight.setOrigin("London");
        flight.setDestination("Bucharest");
        flight.takeOff();
        flight.land();
        assertThrows(RuntimeException.class,
                () -> {
                    flight.setDestination("Sibiu");
                });
    }

    @Test
    public void testLand() {
        Flight flight = new Flight("AA1234", 50);
        flight.setOrigin("London");
        flight.setDestination("Bucharest");
        flight.takeOff();
        assertEquals(true, flight.isTakenOff());
        assertEquals(false, flight.isLanded());
        flight.land();
        assertEquals(true, flight.isTakenOff());
        assertEquals(true, flight.isLanded());
        assertEquals(false, flight.isFlying());
    }

}
