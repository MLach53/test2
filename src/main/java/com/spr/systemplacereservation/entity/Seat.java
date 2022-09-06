package com.spr.systemplacereservation.entity;

import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "SEAT")
public class Seat {

	@Id
	private Integer id;

	@DBRef
	private OfficeBuilding officeBuilding;

	private Integer floorNumber;

	private String seatNumber;

	private boolean reservationEligible;

	private String reDescription;

	// private List<Reservation> reservation = new ArrayList<>();

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

	public Integer getFloorNumber() {
		return floorNumber;
	}

	public void setFloorNumber(Integer flooNumber) {
		this.floorNumber = flooNumber;
	}

	public String getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}

	public boolean getReservationeligible() {
		return reservationEligible;
	}

	public void setReservationeligible(boolean reservationeligible) {
		this.reservationEligible = reservationeligible;
	}

	public String getReDescription() {
		return reDescription;
	}

	public void setReDescription(String reDescription) {
		this.reDescription = reDescription;
	}

	@Override
	public String toString() {
		return "Seat [id=" + id + ", officeBuilding=" + officeBuilding + ", flooNumber=" + floorNumber + ", seatNumber="
				+ seatNumber + ", reservationeligible=" + reservationEligible + ", reDescription=" + reDescription
				+ "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(floorNumber, id, officeBuilding, reDescription, reservationEligible, seatNumber);
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
		return Objects.equals(floorNumber, other.floorNumber) && Objects.equals(id, other.id)
				&& Objects.equals(officeBuilding, other.officeBuilding)
				&& Objects.equals(reDescription, other.reDescription)
				&& Objects.equals(reservationEligible, other.reservationEligible)
				&& Objects.equals(seatNumber, other.seatNumber);
	}

}
