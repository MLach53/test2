package com.spr.systemplacereservation.entity.dto;

import java.time.LocalDate;
import java.util.Objects;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.spr.systemplacereservation.entity.Reservation;

public class ReservationDTO extends ReservationWithoutDateDTO {

	@NotNull

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;

	@NotNull
	private Integer personId;

	private String additionalMessage;

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

	public ReservationDTO(@NotNull @Min(1) String officeBuildingId, @NotNull Integer floorNumber,
			@NotEmpty String seatNumber, @NotNull LocalDate date, @NotNull Integer personId, String additionalMessage,
			String id, LocalDate creationDate) {
		super(officeBuildingId, floorNumber, seatNumber, personId, id, creationDate);

		this.date = date;
		this.additionalMessage = additionalMessage;

	}

	public ReservationDTO() {
		super();
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public void setAdditionalMessage(String additionalMessage) {
		this.additionalMessage = additionalMessage;
	}

	public String getAdditionalMessaage() {
		return additionalMessage;
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
