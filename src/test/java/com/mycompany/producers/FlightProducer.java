package com.mycompany.producers;

import com.mycompany.FlightBuilderUtil;
import com.mycompany.annotations.FlightNumber;
import com.mycompany.Flight;
import java.io.IOException;
import javax.enterprise.inject.Produces;

public class FlightProducer {

    @Produces
    @FlightNumber(number= "AA1234")
    public Flight createFlight() throws IOException {
        return FlightBuilderUtil.buildFlightFromCsv("AA1234", 50,"src/test/resources/flights_information.csv");
    }
    
    @Produces
    @FlightNumber(number= "AA1235")
    public Flight createFlight2() throws IOException {
        return FlightBuilderUtil.buildFlightFromCsv("AA1234", 50,"src/test/resources/flights_information2.csv");
    }
    
    @Produces
    @FlightNumber(number= "AA1236")
    public Flight createFlight3() throws IOException {
        return FlightBuilderUtil.buildFlightFromCsv("AA1234", 50,"src/test/resources/flights_information3.csv");
    }

}
