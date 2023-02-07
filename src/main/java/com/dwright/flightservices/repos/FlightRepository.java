package com.dwright.flightservices.repos;

import java.util.*;
import com.dwright.flightservices.entities.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

public interface FlightRepository extends JpaRepository<Flight, Integer>{
    //If you're wondering about who's going to take care of the more complex implementation, the answer would be Spring

    @Query("from Flight where departureCity=:departureCity and arrivalCity=:arrivalCity and dateOfDeparture=:dateOfDeparture")
    List<Flight> findFlights(@Param("departureCity") String from, @Param("arrivalCity") String to, @Param("dateOfDeparture") Date departureDate);

}
