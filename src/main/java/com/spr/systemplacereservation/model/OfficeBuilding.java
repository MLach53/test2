package com.spr.systemplacereservation.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class OfficeBuilding {
    
    @Id
    private int id;
    
    private String name;
    
    

    public OfficeBuilding() {
    }

    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    @Override
    public int hashCode() {
	return Objects.hash(id, name);
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
	return id == other.id && Objects.equals(name, other.name);
    }
    
    
}
