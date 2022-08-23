package com.spr.systemplacereservation.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.spr.systemplacereservation.entity.Reservation;
import com.spr.systemplacereservation.entity.Seat;
import com.spr.systemplacereservation.entity.dto.ReservationDTO;
import com.spr.systemplacereservation.entity.dto.ReservationWithoutDateDTO;
import com.spr.systemplacereservation.entity.dto.UpdateReservationDTO;
import com.spr.systemplacereservation.exceptions.ValidationException;
import com.spr.systemplacereservation.repository.ReservationRepository;
import com.spr.systemplacereservation.repository.SeatRepository;
import com.spr.systemplacereservation.translator.TranslatorService;

@Service
public class ReservationServiceImpl implements ReservationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReservationServiceImpl.class);

	private final ReservationRepository reservationRepository;
	private final SeatRepository seatRepository;
	private final TranslatorService translator;

	public ReservationServiceImpl(ReservationRepository repository, SeatRepository seatRepository,
			TranslatorService translator) {
		this.reservationRepository = repository;
		this.seatRepository = seatRepository;
		this.translator = translator;
	}

	@Override
	@Transactional
	public Reservation makeReservation(ReservationDTO dto) throws ValidationException {

		Optional<Seat> optional = seatRepository.findByOfficeBuildingIdAndSeatNumberAndFloorNumber(
				dto.getOfficeBuildingId(), dto.getSeatNumber(), dto.getFloorNumber());

		Seat seat = optional.orElseThrow(
				() -> new ValidationException(translator.toLocale("seat_not_found"), HttpStatus.NOT_FOUND));

		if (!seat.getReservationeligible()) {

			throw new ValidationException(translator.toLocale("seat_forbidden"), HttpStatus.FORBIDDEN);
		}

		if (userAlreadyHasReservationInBuilding(dto)) {
			throw new ValidationException("user_has_already_reserved_for_this_building", HttpStatus.LOCKED);
		}

		Reservation reservation = new Reservation();

		reservation.setDate(dto.getDate());
		reservation.setPersonId(dto.getPersonId());
		reservation.setSeat(seat);

		Reservation reservationR = reservationRepository.save(reservation);

		seat.getReservation().add(reservationR);

		return reservationR;

	}

	@Override
	@Transactional
	public void deleteReservation(Integer id) throws ValidationException {
		LOGGER.debug("attempting to delete reservation");

		Reservation reservation = reservationRepository.findById(id)
				.orElseThrow(() -> new ValidationException(translator.toLocale("reservation_delete_not_found"),
						HttpStatus.NOT_FOUND));

		Seat seat = reservation.getSeat();

		seat.getReservation().remove(reservation);

		reservationRepository.delete(reservation);

	}

	public boolean userAlreadyHasReservationInBuilding(ReservationDTO dto) {

		LOGGER.debug("checking one reservation for one building rule for one user...");
		Optional<Reservation> optional = reservationRepository.findFirstByDateAndPersonIdAndOfficeBuildingId(
				dto.getDate(), dto.getPersonId(), dto.getOfficeBuildingId());

		return optional.isPresent();
	}

	@Override
	@Transactional
	public List<ReservationDTO> getReservationsAtGivenDate(LocalDate date) throws ValidationException {

		return reservationRepository.findByDate(date).stream().map(ReservationDTO::convertToDto).toList();

	}

	@Override
	@Transactional
	public Map<LocalDate, List<ReservationWithoutDateDTO>> getReserervationsAtGivenTimeSpan(LocalDate startingDate,
			LocalDate endingDate) throws ValidationException {

		if (startingDate.isAfter(endingDate)) {
			throw new ValidationException(translator.toLocale("starting_date_after_ending_date"),
					HttpStatus.BAD_REQUEST);
		}

		Map<LocalDate, List<ReservationWithoutDateDTO>> map = new HashMap<>();

		reservationRepository.findByDateBetween(startingDate, endingDate).forEach(reservation -> {
			if (map.get(reservation.getDate()) != null) {
				map.get(reservation.getDate()).add(ReservationWithoutDateDTO.convertToDto(reservation));
			} else {
				List<ReservationWithoutDateDTO> list = new ArrayList<>();
				list.add(ReservationWithoutDateDTO.convertToDto(reservation));
				map.put(reservation.getDate(), list);
			}

		});
		return map;
	}

	@Transactional
	public Reservation updateReservationOld(UpdateReservationDTO dto) throws ValidationException {
		deleteReservation(dto.getId());

		return makeReservation(ReservationDTO.convertToDtoFromUpdateDto(dto));
	}

	@Override
	@Transactional
	public Reservation updateReservation(UpdateReservationDTO dto) throws ValidationException {

		Optional<Seat> optionalSeat = seatRepository.findByOfficeBuildingIdAndSeatNumberAndFloorNumber(
				dto.getOfficeBuildingId(), dto.getSeatNumber(), dto.getFloorNumber());

		Seat seat = optionalSeat.orElseThrow(
				() -> new ValidationException(translator.toLocale("seat_not_found"), HttpStatus.NOT_FOUND));

		if (!seat.getReservationeligible()) {

			throw new ValidationException(translator.toLocale("seat_forbidden"), HttpStatus.FORBIDDEN);
		}

		Optional<Reservation> optional = reservationRepository.findById(dto.getId());

		Reservation reservation = optional
				.orElseThrow(() -> new ValidationException(translator.toLocale("reservation_delete_not_found"),
						HttpStatus.NOT_FOUND));

		if (userAlreadyHasReservationInBuilding(ReservationDTO.convertToDtoFromUpdateDto(dto))
				&& !(reservation.getSeat().getOfficeBuilding().getId().equals(dto.getOfficeBuildingId()))) {
			throw new ValidationException(translator.toLocale("user_has_already_reserved_for_this_building"),
					HttpStatus.LOCKED);
		}

		Seat oldSeat = reservation.getSeat();

		reservation.setSeat(seat);
		reservationRepository.save(reservation);

		oldSeat.getReservation().remove(reservation);

		seat.getReservation().add(reservation);

		return reservation;

	}

}