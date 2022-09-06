package com.spr.systemplacereservation.entity.dto;

import java.time.LocalDate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.spr.systemplacereservation.entity.Reservation;

public class ReservationWithoutDateDTO {

	@NotNull
	@Min(1)
	protected String officeBuildingId;

	@NotNull
	protected Integer floorNumber;

	@NotEmpty
	protected String seatNumber;

	@NotNull
	protected Integer personId;

	protected String id;

	protected LocalDate createdOn;

	public static ReservationWithoutDateDTO convertToDto(Reservation reservation) {
		return new ReservationWithoutDateDTO(reservation.getSeat().getOfficeBuilding().getId(),
				reservation.getSeat().getFloorNumber(), reservation.getSeat().getSeatNumber(),
				reservation.getPersonId(), reservation.getId(), reservation.getCreationDate());
	}

	public ReservationWithoutDateDTO(@NotNull @Min(1) String officeBuildingId, @NotNull Integer floorNumber,
			@NotEmpty String seatNumber, @NotNull Integer personId, String id, LocalDate createdOn) {
		super();
		this.officeBuildingId = officeBuildingId;
		this.floorNumber = floorNumber;
		this.seatNumber = seatNumber;
		this.personId = personId;
		this.id = id;
		this.createdOn = createdOn;
	}

	public ReservationWithoutDateDTO() {
		super();
	}

	public String getOfficeBuildingId() {
		return officeBuildingId;
	}

	public void setOfficeBuildingId(String officeBuildingId) {
		this.officeBuildingId = officeBuildingId;
	}

	public Integer getFloorNumber() {
		return floorNumber;
	}

	public void setFloorNumber(Integer floorNumber) {
		this.floorNumber = floorNumber;
	}

	public String getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}

	public Integer getPersonId() {
		return personId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LocalDate getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDate createdOn) {
		this.createdOn = createdOn;
	}

	@Override
	public String toString() {
		return "ReservationWithoutDateDTO [officeBuildingId=" + officeBuildingId + ", floorNumber=" + floorNumber
				+ ", seatNumber=" + seatNumber + ", personId=" + personId + ", id=" + id + "]";
	}

}
