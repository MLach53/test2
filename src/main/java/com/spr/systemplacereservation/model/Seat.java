package com.spr.systemplacereservation.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="SEAT",uniqueConstraints = {
		@UniqueConstraint(name="UC_SEAT", columnNames = {"officebuilding_id", "floor_number", "seat_number"})
})
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "officebuilding_id")
    private OfficeBuilding officeBuilding;

    @Column(name = "floor_number")
    private int flooNumber;

    @Column(name = "seat_number")
    private char seatNumber;

    @OneToMany(mappedBy = "seat")
    private List<Reservation> reservation = new ArrayList<>();

    public Seat() {
    }

    public OfficeBuilding getOfficeBuilding() {
	return officeBuilding;
    }

    public void setOfficeBuilding(OfficeBuilding officeBuilding) {
	this.officeBuilding = officeBuilding;
    }

    public int getFloor_number() {
	return flooNumber;
    }

    public void setFloor_number(int floor_number) {
	this.flooNumber = floor_number;
    }

    public List<Reservation> getReservation() {
	return reservation;
    }

    public void setReservation(List<Reservation> reservation) {
	this.reservation = reservation;
    }

    public int getId() {
	return id;
    }

    @Override
    public int hashCode() {
	return Objects.hash(flooNumber, id, officeBuilding, reservation);
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Seat other = (Seat) obj;
	return flooNumber == other.flooNumber && id == other.id && Objects.equals(officeBuilding, other.officeBuilding)
		&& Objects.equals(reservation, other.reservation);
    }

    @Override
    public String toString() {
	return "Seat [id=" + id + ", officeBuilding=" + officeBuilding + ", floor_number=" + flooNumber
		+ ", seat_number=" + seatNumber + "]";
    }

    public int getSeat_number() {
	return seatNumber;
    }

    public void setSeat_number(int seat_number) {
	this.seatNumber = seat_number;
    }

}
