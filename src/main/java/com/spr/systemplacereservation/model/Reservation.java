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

@Entity(name = "Reservation")
@Table(name = "Reservation")
public class Reservation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="person_id")
	private int personId;
	
	@ManyToOne
	@JoinColumn(name = "seat_id")
	private Seat seat;
	
	@Column(name="date")
	private Date date;
	
	public Reservation() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPerson_id() {
		return personId;
	}

	public void setPerson_id(int person_id) {
		this.personId = person_id;
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
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reservation other = (Reservation) obj;
		return Objects.equals(date, other.date) && id == other.id && personId == other.personId
				;
	}

	@Override
	public String toString() {
		return "Reservation [id=" + id + ", person_id=" + personId + ", seat=" + seat + ", date=" + date + "]";
	}
	
	
	
	
}

