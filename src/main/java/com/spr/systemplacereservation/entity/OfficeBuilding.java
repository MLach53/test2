package com.spr.systemplacereservation.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "OFFICEBUILDING")
public class OfficeBuilding {

	@Id
	private String id;

	@Field(name = "name")
	private String name;

	@DBRef
	private List<Seat> seats = new ArrayList<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Seat> getSeats() {
		return seats;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, seats);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OfficeBuilding other = (OfficeBuilding) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name) && Objects.equals(seats, other.seats);
	}

	@Override
	public String toString() {
		return "OfficeBuilding [id=" + id + ", name=" + name + "]";
	}

}
