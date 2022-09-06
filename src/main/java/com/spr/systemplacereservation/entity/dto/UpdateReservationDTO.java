package com.spr.systemplacereservation.entity.dto;

import java.time.LocalDate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.spr.systemplacereservation.entity.Reservation;

public class UpdateReservationDTO {

	@NotNull
	@Min(1)
	private String officeBuildingId;

	@NotNull
	private Integer floorNumber;

	@NotEmpty
	private String seatNumber;

	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;

	@NotNull
	private Integer personId;

	@NotNull
	private Integer id;

	public static UpdateReservationDTO convertToDto(Reservation reservation) {
		return new UpdateReservationDTO(reservation.getSeat().getOfficeBuilding().getId(),
				reservation.getSeat().getFloorNumber(), reservation.getSeat().getSeatNumber(), reservation.getDate(),
				reservation.getPersonId(), reservation.getId());
	}

	public UpdateReservationDTO(@NotNull @Min(1) String officeBuildingId, @NotNull Integer floorNumber,
			@NotEmpty String seatNumber, @NotNull LocalDate date, @NotNull Integer personId, Integer id) {
		super();
		this.officeBuildingId = officeBuildingId;
		this.floorNumber = floorNumber;
		this.seatNumber = seatNumber;
		this.date = date;
		this.personId = personId;
		this.id = id;
	}

	public UpdateReservationDTO() {
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

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Integer getPersonId() {
		return personId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
