package com.spr.systemplacereservation.model;

import java.sql.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "RESERVATION", uniqueConstraints = {
	@UniqueConstraint(name = "UC_RESERVATION", columnNames = { "seat_id", "date" }) })
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "person_id")
    private Integer personId;

    @ManyToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;

    @Column(name = "date")
    private Date date;

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
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

    public Date getDate() {
	return date;
    }

    public void setDate(Date date) {
	this.date = date;
    }

    @Override
    public int hashCode() {
	return Objects.hash(date, id, personId, seat);
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
	return Objects.equals(date, other.date) && Objects.equals(id, other.id)
		&& Objects.equals(personId, other.personId) && Objects.equals(seat, other.seat);
    }

    @Override
    public String toString() {
	return "Reservation [id=" + id + ", personId=" + personId + ", seat=" + seat + ", date=" + date + "]";
    }

}
