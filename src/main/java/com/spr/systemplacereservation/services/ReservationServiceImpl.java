package com.spr.systemplacereservation.services;

import java.util.Optional;

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

	private Logger logger = LoggerFactory.getLogger(ReservationServiceImpl.class);

	private ReservationRepository reservationRepository;
	private VCheckReservationRepository vCheckReservationRepository;
	private SeatRepository seatRepository;

	@Autowired
	Translator translator;

	public ReservationServiceImpl(ReservationRepository repository, SeatRepository seatRepository,
			VCheckReservationRepository vCheckReservationRepository) {
		this.reservationRepository = repository;
		this.seatRepository = seatRepository;
		this.vCheckReservationRepository = vCheckReservationRepository;
	}

	@Override
	@Transactional
	public Reservation makeReservation(ReservationDTO dto) {

		Reservation reservation = new Reservation();

		reservation.setDate(dto.getDate());
		reservation.setPersonId(dto.getPersonId());

		// SeatQuery query = new SeatQuery();

		// query.setBuildingOffice(dto.getOfficeBuildingId());
		// query.setFloorNumber(dto.getFloorNumber());
		// query.setSeatNumber(dto.getSeatNumber());

		// Seat seat = seatRepositoryDAO.findSeatBy(query);

		Optional<Seat> optional = seatRepository.findByOfficeBuildingIdAndSeatNumberAndFloorNumber(
				dto.getOfficeBuildingId(), dto.getSeatNumber(), dto.getFloorNumber());

		Seat seat = optional.orElseThrow();

		if (!seat.getReservationeligible().booleanValue()) {

			throw new NotAvailableException(translator.toLocale("chair_forbidden"));
		}

		if (userAlreadyHasRegistraionInBuilding(dto)) {
			throw new UserAlreadyReservedChairException("user_has_already_reserved_for_this_building");
		}

		reservation.setSeat(seat);

		reservation.setDate(dto.getDate());

		return reservationRepository.save(reservation);

	}

	@Override
	@Transactional
	public void deleteReservation(Integer id) {
		logger.info("attempting to delete reservation");

		Reservation reservation = reservationRepository.findById(id).orElseThrow();

		reservationRepository.delete(reservation);
	}

	public boolean userAlreadyHasRegistraionInBuilding(ReservationDTO dto) {
		logger.info("checking one reservation for one building rule for one user...");
		Optional<VCheckReservation> optional = vCheckReservationRepository
				.findFirstByDateAndPersonIdAndOfficeBuildingId(dto.getDate(), dto.getPersonId(),
						dto.getOfficeBuildingId());

		return optional.isPresent();
	}

}
