package com.dwright.flightservices.entities;

import jakarta.persistence.*;

import java.sql.*;
import java.util.Date;

@Entity
@Table(name = "RESERVATION")
public class Reservation extends AbstractEntity{
    private boolean checkedIn;
    private int numberOfBags;
    @Column(name="PASSENGER_ID", insertable = false, updatable = false)
    private int passengerId;
    @OneToOne
    private Flight flight;
    @OneToOne
    private Passenger passenger;

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public boolean isCheckedIn() {
        return checkedIn;
    }

    public void setCheckedIn(boolean checkedIn) {
        this.checkedIn = checkedIn;
    }

    public int getNumberOfBags() {
        return numberOfBags;
    }

    public void setNumberOfBags(int numberOfBags) {
        this.numberOfBags = numberOfBags;
    }

    public int getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(int passengerId) {
        this.passengerId = passengerId;
    }

}
