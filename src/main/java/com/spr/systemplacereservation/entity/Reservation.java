package com.spr.systemplacereservation.entity;

import java.time.LocalDate;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "RESERVATION")
public class Reservation {

    @Id
    private String id;

    @Field(value = "person_id")
    private Integer personId;

    @DBRef
    @Field(value = "seat_id")
    private Seat seat;

    @Field(value = "date")
    private LocalDate date;

    @Field(value = "created_on")
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

    public Seat getSeat() {
	return seat;
    }

    public void setSeat(Seat seat) {
	this.seat = seat;
    }

    public LocalDate getDate() {
	return date;
    }

    public void setDate(LocalDate date) {
	this.date = date;
    }

    public LocalDate getCreationDate() {
	return creationOn;
    }

    public void setCreationDate(LocalDate creationDate) {
	this.creationOn = creationDate;
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
