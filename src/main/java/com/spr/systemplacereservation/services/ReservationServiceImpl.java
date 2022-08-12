package com.spr.systemplacereservation.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spr.systemplacereservation.entity.Reservation;
import com.spr.systemplacereservation.entity.Seat;
import com.spr.systemplacereservation.entity.VCheckReservation;
import com.spr.systemplacereservation.entity.dto.ReservationDTO;
import com.spr.systemplacereservation.exceptions.NotAvailableException;
import com.spr.systemplacereservation.exceptions.UserAlreadyReservedChairException;
import com.spr.systemplacereservation.repository.ReservationRepository;
import com.spr.systemplacereservation.repository.SeatRepository;
import com.spr.systemplacereservation.repository.VCheckReservationRepository;
import com.spr.systemplacereservation.translator.Translator;

@Service
public class ReservationServiceImpl implements ReservationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationServiceImpl.class);

    private ReservationRepository reservationRepository;
    private VCheckReservationRepository vCheckReservationRepository;
    private SeatRepository seatRepository;

    @Autowired
    private Translator translator;

    public ReservationServiceImpl(ReservationRepository repository, SeatRepository seatRepository,
	    VCheckReservationRepository vCheckReservationRepository) {
	this.reservationRepository = repository;
	this.seatRepository = seatRepository;
	this.vCheckReservationRepository = vCheckReservationRepository;
    }

    @Override
    @Transactional
    public Reservation makeReservation(ReservationDTO dto) {

	Optional<Seat> optional = seatRepository.findByOfficeBuildingIdAndSeatNumberAndFloorNumber(
		dto.getOfficeBuildingId(), dto.getSeatNumber(), dto.getFloorNumber());

	Seat seat = optional.orElseThrow();

	if (!seat.getReservationeligible()) {

	    throw new NotAvailableException(translator.toLocale("chair_forbidden"));
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
	Optional<VCheckReservation> optional = vCheckReservationRepository
		.findFirstByDateAndPersonIdAndOfficeBuildingId(dto.getDate(), dto.getPersonId(),
			dto.getOfficeBuildingId());

	return optional.isPresent();
    }

    @Override
    public List<ReservationDTO> getReservationsAtGivenDate(LocalDate date) {

	return reservationRepository.findByDate(date).stream().map(reservation -> {
	    return ReservationDTO.convertToDto(reservation);
	}).collect(Collectors.toList());

    }

}
