package com.spr.systemplacereservation.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Field.Write;

@Document(collection = "RESERVATION")
public class Reservation {

	@Id
	private String id;

	@Field("personId")
	private Integer personId;

	@Field(write = Write.NON_NULL)
	private Seat seat;
	
	@Field("seat_id")
	private ObjectId seatId;

	private LocalDateTime date;

	@Field("createdOn")
	private LocalDate creationOn;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getPersonId() {
		return personId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	


	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public LocalDate getCreationDate() {
		return creationOn;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationOn = creationDate;
	}
	
	public Seat getSeat() {
		return seat;
	}

	public void setSeat(Seat seat) {
		this.seat = seat;
	}

	public LocalDate getCreationOn() {
		return creationOn;
	}

	public void setCreationOn(LocalDate creationOn) {
		this.creationOn = creationOn;
	}

	
	
	public ObjectId getSeatId() {
		return seatId;
	}

	public void setSeatId(ObjectId seatId) {
		this.seatId = seatId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(creationOn, date, id, personId, seat);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reservation other = (Reservation) obj;
		return Objects.equals(creationOn, other.creationOn) && Objects.equals(date, other.date)
				&& Objects.equals(id, other.id) && Objects.equals(personId, other.personId)
				&& Objects.equals(seat, other.seat);
	}

	@Override
	public String toString() {
		return "Reservation [id=" + id + ", creationDate=" + creationOn + ", personId=" + personId + ", seat=" + seat
				+ ", date=" + date + "]";
	}

}
