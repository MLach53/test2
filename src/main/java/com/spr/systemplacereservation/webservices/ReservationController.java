package com.spr.systemplacereservation.webservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spr.systemplacereservation.entity.Reservation;
import com.spr.systemplacereservation.entity.dto.ReservationDTO;
import com.spr.systemplacereservation.exceptions.NotAvailableException;
import com.spr.systemplacereservation.services.ReservationService;
import com.spr.systemplacereservation.translator.Translator;

@RestController
@Validated
public class ReservationController {

	
	@Autowired
	ReservationService service;
	
	@PostMapping(path = "/reservation/make", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity makeReservation(@RequestBody ReservationDTO dto) {
		System.out.println(Translator.toLocale("chair_forbidden"));
		
		
		try {
			Reservation res = service.makeReservation(dto);
			
			System.out.println(res);
			
			
			return new ResponseEntity<>(dto, HttpStatus.OK);
			
		} catch (DataIntegrityViolationException e){
			
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		} catch(EmptyResultDataAccessException e) {
			
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch(NotAvailableException e ) {
			
			
			return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
			
		}
		
		
		
		
	}
	
}
