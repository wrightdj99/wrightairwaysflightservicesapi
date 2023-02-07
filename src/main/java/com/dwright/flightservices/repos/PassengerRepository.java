package com.dwright.flightservices.repos;

import com.dwright.flightservices.entities.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerRepository extends JpaRepository<Passenger, Integer>{
    //Spring will take care of the implementation here...

}
