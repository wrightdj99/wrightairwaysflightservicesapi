package com.dwright.flightservices.repos;

import com.dwright.flightservices.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Integer>{
    //Spring will take care of the implementation here...

}
