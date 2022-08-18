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
import org.springframework.stereotype.Service;

import com.spr.systemplacereservation.entity.Reservation;
import com.spr.systemplacereservation.entity.Seat;
import com.spr.systemplacereservation.entity.dto.ReservationDTO;
import com.spr.systemplacereservation.entity.dto.ReservationWithoutDateDTO;
import com.spr.systemplacereservation.entity.dto.UpdateReservationDTO;
import com.spr.systemplacereservation.exceptions.ChairNotAvailableException;
import com.spr.systemplacereservation.exceptions.UserAlreadyReservedChairException;
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
    public Reservation makeReservation(ReservationDTO dto) {

	Optional<Seat> optional = seatRepository.findByOfficeBuildingIdAndSeatNumberAndFloorNumber(
		dto.getOfficeBuildingId(), dto.getSeatNumber(), dto.getFloorNumber());

	Seat seat = optional.orElseThrow();

	if (!seat.getReservationeligible()) {

	    throw new ChairNotAvailableException(translator.toLocale("chair_forbidden"));
	}

	if (userAlreadyHasReservationInBuilding(dto)) {
	    throw new UserAlreadyReservedChairException("user_has_already_reserved_for_this_building");
	}

	Reservation reservation = new Reservation();

	reservation.setDate(dto.getDate());
	reservation.setPersonId(dto.getPersonId());
	reservation.setSeat(seat);
	reservation.setDate(dto.getDate());

	return reservationRepository.save(reservation);

    }

    @Override
    @Transactional
    public void deleteReservation(Integer id) {
	LOGGER.debug("attempting to delete reservation");

	// Reservation reservation = reservationRepository.findById(id).orElseThrow();

	reservationRepository.deleteById(id);
    }

    public boolean userAlreadyHasReservationInBuilding(ReservationDTO dto) {

	LOGGER.debug("checking one reservation for one building rule for one user...");
	Optional<Reservation> optional = reservationRepository.findFirstByDateAndPersonIdAndOfficeBuildingId(
		dto.getDate(), dto.getPersonId(), dto.getOfficeBuildingId());

	return optional.isPresent();
    }

    @Override
    public List<ReservationDTO> getReservationsAtGivenDate(LocalDate date) {

	return reservationRepository.findByDate(date).stream().map(ReservationDTO::convertToDto).toList();

    }

    @Override
    public Map<LocalDate, List<ReservationWithoutDateDTO>> getReserervationsAtGivenTimeSpan(LocalDate startingDate,
	    LocalDate endingDate) {

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

    @Override
    @Transactional
    public Reservation updateReservation(UpdateReservationDTO dto) {
	deleteReservation(dto.getId());

	return makeReservation(ReservationDTO.convertToDtoFromUpdateDto(dto));
    }

}
