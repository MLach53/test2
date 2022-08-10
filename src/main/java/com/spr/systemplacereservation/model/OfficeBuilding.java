package com.spr.systemplacereservation.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "officebuilding")
public class OfficeBuilding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany(mappedBy = "officeBuilding")
    private List<Seat> seats = new ArrayList<>();

    public OfficeBuilding() {
    }

    public int getId() {
	return id;
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
	return id == other.id && Objects.equals(name, other.name) && Objects.equals(seats, other.seats);
    }

}
