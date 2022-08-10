package com.spr.systemplacereservation.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "officebuilding_id")
    private OfficeBuilding officeBuilding;

    private int floor_number;

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
	return floor_number;
    }

    public void setFloor_number(int floor_number) {
	this.floor_number = floor_number;
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
	return Objects.hash(floor_number, id, officeBuilding, reservation);
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
	return floor_number == other.floor_number && id == other.id
		&& Objects.equals(officeBuilding, other.officeBuilding)
		&& Objects.equals(reservation, other.reservation);
    }

    @Override
    public String toString() {
	return "Seat [id=" + id + ", officeBuilding=" + officeBuilding + ", floor_number=" + floor_number + "]";
    }

}
