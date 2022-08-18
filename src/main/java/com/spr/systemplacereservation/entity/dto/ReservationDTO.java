package com.spr.systemplacereservation.entity.dto;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.spr.systemplacereservation.entity.Reservation;

public class ReservationDTO {

    @NotNull
    @Min(1)
    private Integer officeBuildingId;

    @NotNull
    private Integer floorNumber;

    @NotEmpty
    private String seatNumber;

    @NotNull
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @NotNull
    private Integer personId;

    private String additionalMessage;

    private LocalDate createdOn;

    private Integer id;

    public static ReservationDTO convertToDto(Reservation reservation) {
	return new ReservationDTO(reservation.getSeat().getOfficeBuilding().getId(),
		reservation.getSeat().getFloorNumber(), reservation.getSeat().getSeatNumber(), reservation.getDate(),
		reservation.getPersonId(), null, reservation.getId(), reservation.getCreationDate());
    }

    public static ReservationDTO convertToDtoFromUpdateDto(UpdateReservationDTO reservation) {
	return new ReservationDTO(reservation.getOfficeBuildingId(), reservation.getFloorNumber(),
		reservation.getSeatNumber(), reservation.getDate(), reservation.getPersonId(), null,
		reservation.getId(), null);
    }

    public ReservationDTO(@NotNull @Min(1) Integer officeBuildingId, @NotNull Integer floorNumber,
	    @NotEmpty String seatNumber, @NotNull LocalDate date, @NotNull Integer personId, String additionalMessage,
	    Integer id, LocalDate creationDate) {
	super();
	this.officeBuildingId = officeBuildingId;
	this.floorNumber = floorNumber;
	this.seatNumber = seatNumber;
	this.date = date;
	this.personId = personId;
	this.additionalMessage = additionalMessage;
	this.id = id;
	this.createdOn = creationDate;
    }

    public ReservationDTO() {
	super();
    }

    public Integer getOfficeBuildingId() {
	return officeBuildingId;
    }

    public void setOfficeBuildingId(Integer officeBuildingId) {
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

    public void setAdditionalMessage(String additionalMessage) {
	this.additionalMessage = additionalMessage;
    }

    public String getAdditionalMessaage() {
	return additionalMessage;
    }

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
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
	return "ReservationDTO [officeBuildingId=" + officeBuildingId + ", floorNumber=" + floorNumber + ", seatNumber="
		+ seatNumber + ", date=" + date + ", personId=" + personId + ", additionalMessage=" + additionalMessage
		+ ", createdOn=" + createdOn + ", id=" + id + "]";
    }

    @Override
    public int hashCode() {
	return Objects.hash(additionalMessage, createdOn, date, floorNumber, id, officeBuildingId, personId,
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
	ReservationDTO other = (ReservationDTO) obj;
	return Objects.equals(additionalMessage, other.additionalMessage) && Objects.equals(createdOn, other.createdOn)
		&& Objects.equals(date, other.date) && Objects.equals(floorNumber, other.floorNumber)
		&& Objects.equals(id, other.id) && Objects.equals(officeBuildingId, other.officeBuildingId)
		&& Objects.equals(personId, other.personId) && Objects.equals(seatNumber, other.seatNumber);
    }

}
