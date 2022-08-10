package com.spr.systemplacereservation.services;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.spr.systemplacereservation.entity.Reservation;
import com.spr.systemplacereservation.entity.Seat;
import com.spr.systemplacereservation.entity.dto.ReservationDTO;
import com.spr.systemplacereservation.exceptions.NotAvailableException;
import com.spr.systemplacereservation.repository.ReservationRepository;
import com.spr.systemplacereservation.repository.SeatRepositoryDAO;
import com.spr.systemplacereservation.repository.SeatRepositoryDAO.SeatQuery;
import com.spr.systemplacereservation.translator.Translator;

@Service
public class ReservationServiceImpl implements ReservationService {

	private ReservationRepository repository;
	private SeatRepositoryDAO seatRepositoryDAO;

	public ReservationServiceImpl(ReservationRepository repository, SeatRepositoryDAO seatRepositoryDAO) {
		this.repository = repository;
		this.seatRepositoryDAO = seatRepositoryDAO;
	}

	@Override
	@Transactional
	public Reservation makeReservation(ReservationDTO dto) {

		Reservation reservation = new Reservation();

		reservation.setDate(dto.getDate());
		reservation.setPersonId(dto.getPersonId());

		SeatQuery query = new SeatQuery();

		query.setBuildingOffice(dto.getOfficeBuildingId());
		query.setFloorNumber(dto.getFloorNumber());
		query.setSeatNumber(dto.getSeatNumber());

		Seat seat = seatRepositoryDAO.findSeatBy(query);

		if (!seat.getReservationeligible()) {

			// wyjetek
			throw new NotAvailableException(Translator.toLocale("chair_forbidden"));
		}

		reservation.setSeat(seat);

		reservation.setDate(dto.getDate());

		return repository.save(reservation);

	}

}
