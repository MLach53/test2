package com.spr.systemplacereservation.entity;

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
@Table(name = "SEAT", uniqueConstraints = {
	@UniqueConstraint(name = "UC_SEAT", columnNames = { "officebuilding_id", "floor_number", "seat_number" }) })
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "officebuilding_id")
    private OfficeBuilding officeBuilding;

    @Column(name = "floor_number")
    private Integer flooNumber;

    @Column(name = "seat_number")
    private String seatNumber;

    @OneToMany(mappedBy = "seat")
    private List<Reservation> reservation = new ArrayList<>();

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public OfficeBuilding getOfficeBuilding() {
	return officeBuilding;
    }

    public void setOfficeBuilding(OfficeBuilding officeBuilding) {
	this.officeBuilding = officeBuilding;
    }

    public Integer getFlooNumber() {
	return flooNumber;
    }

    public void setFlooNumber(Integer flooNumber) {
	this.flooNumber = flooNumber;
    }

    public String getSeatNumber() {
	return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
	this.seatNumber = seatNumber;
    }

    public List<Reservation> getReservation() {
	return reservation;
    }

    public void setReservation(List<Reservation> reservation) {
	this.reservation = reservation;
    }

    @Override
    public int hashCode() {
	return Objects.hash(flooNumber, id, officeBuilding, reservation, seatNumber);
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
	return Objects.equals(flooNumber, other.flooNumber) && Objects.equals(id, other.id)
		&& Objects.equals(officeBuilding, other.officeBuilding)
		&& Objects.equals(reservation, other.reservation) && Objects.equals(seatNumber, other.seatNumber);
    }

    @Override
    public String toString() {
	return "Seat [id=" + id + ", officeBuilding=" + officeBuilding + ", flooNumber=" + flooNumber + ", seatNumber="
		+ seatNumber + "]";
    }

}
