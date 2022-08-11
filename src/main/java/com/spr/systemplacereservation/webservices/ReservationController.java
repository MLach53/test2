package com.spr.systemplacereservation.webservices;

import java.util.NoSuchElementException;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spr.systemplacereservation.entity.Reservation;
import com.spr.systemplacereservation.entity.dto.ReservationDTO;
import com.spr.systemplacereservation.exceptions.NotAvailableException;
import com.spr.systemplacereservation.exceptions.UserAlreadyReservedChairException;
import com.spr.systemplacereservation.services.ReservationService;
import com.spr.systemplacereservation.translator.Translator;

@RestController
@Validated
public class ReservationController {

	@Autowired
	ReservationService service;

	@Autowired
	Translator translator;

	@PostMapping(path = "/reservation", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> makeReservation(@RequestBody @Valid ReservationDTO dto) {

		try {
			Reservation reservation = service.makeReservation(dto);

			dto.setAdditionalMessage(translator.toLocale("reservation_post_succesful"));

			dto.setId(reservation.getId());

			return new ResponseEntity<>(dto, HttpStatus.OK);

		} catch (DataIntegrityViolationException e) {

			return new ResponseEntity<>(
					e.getMessage() + "\n" + translator.toLocale("reservation_post_constraint_violation"),
					HttpStatus.CONFLICT);
		} catch (NoSuchElementException e) {

			return new ResponseEntity<>(
					e.getMessage() + "\n" + translator.toLocale("reservation_not_existing_seat_or_else"),
					HttpStatus.NOT_FOUND);
		} catch (NotAvailableException e) {

			return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);

		} catch (UserAlreadyReservedChairException e) {

			return new ResponseEntity<>(e.getMessage(), HttpStatus.LOCKED);
		}

	}

	@DeleteMapping(path = "/reservation")
	public ResponseEntity<Object> deleteReservation(@RequestParam(name = "id") @Valid @NonNull @Min(1) Integer id) {
		try {

			service.deleteReservation(id);

		} catch (NoSuchElementException e) {

			return new ResponseEntity<>(
					e.getLocalizedMessage() + "\n" + translator.toLocale("reservation_delete_not_found"),
					HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(translator.toLocale("reservation_deleted"), HttpStatus.OK);

	}

}
