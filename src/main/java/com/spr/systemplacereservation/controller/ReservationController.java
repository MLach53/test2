package com.spr.systemplacereservation.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spr.systemplacereservation.entity.Reservation;
import com.spr.systemplacereservation.entity.dto.ReservationDTO;
import com.spr.systemplacereservation.entity.dto.UpdateReservationDTO;
import com.spr.systemplacereservation.exceptions.ChronologicalException;
import com.spr.systemplacereservation.exceptions.SeatNotAvailableException;
import com.spr.systemplacereservation.exceptions.UserAlreadyReservedChairException;
import com.spr.systemplacereservation.services.ReservationService;
import com.spr.systemplacereservation.translator.TranslatorService;

@RestController
@Validated
public class ReservationController {

	@Autowired
	private ReservationService service;

	@Autowired
	private TranslatorService translator;

	@PostMapping(path = "/reservation", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> makeReservation(@RequestBody @Valid ReservationDTO dto) {

		try {
			Reservation reservation = service.makeReservation(dto);

			dto.setAdditionalMessage(translator.toLocale("reservation_post_succesful"));

			dto.setId(reservation.getId());
			dto.setCreatedOn(reservation.getCreationDate());

			return new ResponseEntity<>(dto, HttpStatus.OK);

		} catch (DataIntegrityViolationException e) {

			return new ResponseEntity<>(
					e.getMessage() + "\n" + translator.toLocale("reservation_post_constraint_violation"),
					HttpStatus.CONFLICT);
		} catch (NoSuchElementException e) {

			return new ResponseEntity<>(
					e.getMessage() + "\n" + translator.toLocale("reservation_not_existing_seat_or_else"),
					HttpStatus.NOT_FOUND);
		} catch (SeatNotAvailableException e) {

			return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);

		} catch (UserAlreadyReservedChairException e) {

			return new ResponseEntity<>(e.getMessage(), HttpStatus.LOCKED);
		}

	}

	@DeleteMapping(path = "/reservation")
	public ResponseEntity<Object> deleteReservation(@RequestParam(name = "id") @Valid @NonNull @Min(1) Integer id) {
		try {

			service.deleteReservation(id);

		} catch (EmptyResultDataAccessException e) {

			return new ResponseEntity<>(
					e.getLocalizedMessage() + "\n" + translator.toLocale("reservation_delete_not_found"),
					HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(translator.toLocale("reservation_deleted"), HttpStatus.OK);

	}

	@GetMapping(path = "/reservation/byOneDate")
	public ResponseEntity<List<ReservationDTO>> getReservationsAtGivenDate(
			@RequestParam(name = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
		return new ResponseEntity<>(service.getReservationsAtGivenDate(date), HttpStatus.OK);
	}

	@GetMapping(path = "/reservation")
	public ResponseEntity<Object> getReserervationsAtGivenTimeSpan(
			@RequestParam(name = "starting_date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startingDate,
			@RequestParam(name = "ending_date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endingDate) {

		try {
			return new ResponseEntity<>(service.getReserervationsAtGivenTimeSpan(startingDate, endingDate),
					HttpStatus.OK);
		} catch (ChronologicalException e) {
			return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@PutMapping(path = "/reservation", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updateReservation(@RequestBody @Valid UpdateReservationDTO dto) {

		try {

			Reservation reservation = service.updateReservation(dto);

			return new ResponseEntity<>(ReservationDTO.convertToDto(reservation), HttpStatus.OK);

		} catch (DataIntegrityViolationException e) {

			return new ResponseEntity<>(
					e.getMessage() + "\n" + translator.toLocale("reservation_post_constraint_violation"),
					HttpStatus.CONFLICT);
		} catch (NoSuchElementException e) {

			return new ResponseEntity<>(
					e.getMessage() + "\n" + translator.toLocale("reservation_not_existing_seat_or_else"),
					HttpStatus.NOT_FOUND);
		} catch (SeatNotAvailableException e) {

			return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);

		} catch (UserAlreadyReservedChairException e) {

			return new ResponseEntity<>(e.getMessage(), HttpStatus.LOCKED);
		}

	}

}
