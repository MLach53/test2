package com.spr.systemplacereservation.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.spr.systemplacereservation.entity.Reservation;
import com.spr.systemplacereservation.entity.dto.ReservationDTO;
import com.spr.systemplacereservation.entity.dto.ReservationWithoutDateDTO;
import com.spr.systemplacereservation.entity.dto.UpdateReservationDTO;
import com.spr.systemplacereservation.exceptions.ValidationException;

public interface ReservationService {

	public Reservation makeReservation(ReservationDTO dto) throws ValidationException;

	public void deleteReservation(Integer id) throws ValidationException;

	public List<ReservationDTO> getReservationsAtGivenDate(LocalDate date) throws ValidationException;

	public Map<LocalDate, List<ReservationWithoutDateDTO>> getReserervationsAtGivenTimeSpan(LocalDate startingDate,
			LocalDate endingDate) throws ValidationException;

	public Reservation updateReservation(UpdateReservationDTO dto) throws ValidationException;

}
