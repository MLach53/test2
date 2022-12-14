package com.spr.systemplacereservation.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "officebuilding_id")
	private OfficeBuilding officeBuilding;

	@Column(name = "floor_number")
	private Integer floorNumber;

	@Column(name = "seat_number")
	private String seatNumber;

	@Column(name = "reservationeligible")
	private boolean reservationEligible;

	@Column(name = "redescription")
	private String reDescription;

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

	public List<Reservation> getReservation() {
		return reservation;
	}

	public void setReservation(List<Reservation> reservation) {
		this.reservation = reservation;
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
		return Objects.hash(floorNumber, id, officeBuilding, reDescription, reservation, reservationEligible,
				seatNumber);
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
				&& Objects.equals(reDescription, other.reDescription) && Objects.equals(reservation, other.reservation)
				&& Objects.equals(reservationEligible, other.reservationEligible)
				&& Objects.equals(seatNumber, other.seatNumber);
	}

}
