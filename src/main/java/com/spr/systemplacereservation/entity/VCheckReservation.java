package com.spr.systemplacereservation.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Immutable;

@Entity(name = "vcheckreservations")
@Immutable
public class VCheckReservation {

	@Id
	@Column(name = "id")
	private Integer id;
	@Column(name = "person_id")
	private Integer personId;

	@Column(name = "officebuilding_id")
	private Integer officeBuildingId;

	@Column(name = "seat_id")
	private Integer seatId;

	@Column(name = "date")
	@Temporal(TemporalType.DATE)
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

	public Integer getOfficeBuildingId() {
		return officeBuildingId;
	}

	public void setOfficeBuildingId(Integer officeBuildingId) {
		this.officeBuildingId = officeBuildingId;
	}

	public Integer getSeatId() {
		return seatId;
	}

	public void setSeatId(Integer seatId) {
		this.seatId = seatId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "VCheckReservation [id=" + id + ", personId=" + personId + ", officeBuildingId=" + officeBuildingId
				+ ", seatId=" + seatId + ", date=" + date + "]";
	}

}
