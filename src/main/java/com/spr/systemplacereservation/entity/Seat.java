package com.spr.systemplacereservation.entity;

import java.util.Objects;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "SEAT")
public class Seat {

	@Id
	private ObjectId id;

	@Field(value = "officebuilding_id")
	private ObjectId officeBuildingId;

	private OfficeBuilding officeBuilding;
	
	@Field(value = "floor_number")
	private Integer floorNumber;

	@Field(value = "seat_number")
	private String seatNumber;

	@Field(value = "re")
	private boolean reservationEligible = true;

	@Field(value = "description")
	private String reDescription;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public ObjectId getOfficeBuildingId() {
		return officeBuildingId;
	}

	public void setOfficeBuildingId(ObjectId officeBuilding) {
		this.officeBuildingId = officeBuilding;
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
		return "Seat [id=" + id + ", officeBuildingId=" + officeBuildingId + ", officeBuilding=" + officeBuilding
				+ ", floorNumber=" + floorNumber + ", seatNumber=" + seatNumber + ", reservationEligible="
				+ reservationEligible + ", reDescription=" + reDescription + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(floorNumber, id, officeBuildingId, reDescription, reservationEligible, seatNumber);
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
				&& Objects.equals(officeBuildingId, other.officeBuildingId)
				&& Objects.equals(reDescription, other.reDescription)
				&& Objects.equals(reservationEligible, other.reservationEligible)
				&& Objects.equals(seatNumber, other.seatNumber);
	}

}
