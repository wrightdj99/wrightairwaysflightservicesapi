package com.dwright.flightservices.integration;

import com.dwright.flightservices.dto.CreateReservationRequest;
import com.dwright.flightservices.dto.UpdateReservationRequest;
import com.dwright.flightservices.entities.Flight;
import java.util.*;

import com.dwright.flightservices.entities.Passenger;
import com.dwright.flightservices.entities.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.dwright.flightservices.repos.FlightRepository;
import com.dwright.flightservices.repos.PassengerRepository;
import com.dwright.flightservices.repos.ReservationRepository;

@RestController
@CrossOrigin
public class ReservationRestController {

    //Making that connection to our JPA API to get data (see: anything with the word Repository) in it.
    //Furthermore, we are using dependency injection here so that Spring creates a reference to our Repository
    //defined in our Repos folder at runtime.

    //Getting our three repositories in to connect to various DB tables
    @Autowired
    FlightRepository flightRepository;
    @Autowired
    PassengerRepository passengerRepository;
    @Autowired
    ReservationRepository reservationRepository;

    /*Getting information about filtered flights*/
    @RequestMapping(value = "/flights", method = RequestMethod.GET)
    public List<Flight> findFilteredFlights(@RequestParam("from") String from, @RequestParam("to") String to,
                                            @RequestParam("departureDate") @DateTimeFormat(pattern = "MM-dd-yyyy")
                                           Date departureDate){
        return flightRepository.findFlights(from, to, departureDate);
    }

    /*Getting a list of all flights*/
    @RequestMapping(value = "/flights/", method = RequestMethod.GET)
    public List<Flight> findAllFlights(){
        return flightRepository.findAll();
    }

    @RequestMapping(value = "/flights/{flightId}", method = RequestMethod.GET)
    public Flight findFlight(@PathVariable("flightId")int flightId){
        return flightRepository.findById(flightId).get();
    }


    @RequestMapping(value = "/reservations", method = RequestMethod.POST)
    @Transactional
    public Reservation saveReservation(@RequestBody CreateReservationRequest request) {
        //Getting Flight ID
        Flight flight = flightRepository.findById(request.getFlightId()).get();

        //Saving all fields necessary for Passenger class
        Passenger passenger = new Passenger();
        passenger.setFirstName(request.getPassengerFirstName());
        passenger.setLastName(request.getPassengerLastName());
        passenger.setMiddleName(request.getPassengerMiddleName());
        passenger.setEmail(request.getPassengerEmail());
        passenger.setPhone(request.getPassengerPhone());

        //Saving savedPassenger to Repository
        Passenger savedPassenger = passengerRepository.save(passenger);

        //Making a new instance of reservation and saving our flight, passenger and checkedIn statuses
        Reservation reservation = new Reservation();
        reservation.setFlight(flight);
        reservation.setPassenger(savedPassenger);
        reservation.setCheckedIn(false);

        //Incidentally, because this method returns a Reservation, you can just return the saving.
        return reservationRepository.save(reservation);

    }

    /*
    * Get info about a reservation with a passed in ID.
    * Furthermore, we are using injection with the @PathVariable tag to get that property from
    * Spring; we can retrieve the ID from the URL (or later on, the search field that the end user will type in)
    * */
    @RequestMapping(value = "/reservations/{id}", method = RequestMethod.GET)
    public Reservation findReservation(@PathVariable("id") int id){
        //Isn't this cool? findById is built into the API so we can just call it!
        return reservationRepository.findById(id).get();
    }

    /*
    * Time for a PUT method!
    *
    * */
    @RequestMapping(value = "/reservations", method = RequestMethod.PUT)
    public Reservation updateReservation(@RequestBody UpdateReservationRequest request){
        Reservation reservation = reservationRepository.findById(request.getReservationID()).get();
        reservation.setNumberOfBags(request.getNumberOfBags());
        reservation.setCheckedIn(request.isCheckIn());
        return reservationRepository.save(reservation);
    }
}
