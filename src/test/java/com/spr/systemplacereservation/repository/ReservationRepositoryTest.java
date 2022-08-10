package com.spr.systemplacereservation.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.spr.systemplacereservation.SystemplacereservationApplicationTests;

class ReservationRepositoryTest extends SystemplacereservationApplicationTests {

	@Autowired
	ReservationRepository repository;
	
	@Autowired
	DataSource source;
	
	@Test
	void test() throws SQLException {
	
		System.out.println(source.getConnection());
		
		PreparedStatement statement = source.getConnection().prepareStatement("select * from Reservation");
		
		ResultSet set = statement.executeQuery();
		
		while(set.next()) {
			
			System.out.println(set.getInt("id"));
		}
		
		System.out.println(repository.findById(1));
		
		//System.out.println(repository.count());
		
		//System.out.println(repository.findAll());
		
	}

}
