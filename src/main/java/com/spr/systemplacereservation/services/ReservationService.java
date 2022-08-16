package com.spr.systemplacereservation.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.spr.systemplacereservation.entity.Reservation;
import com.spr.systemplacereservation.entity.dto.ReservationDTO;
import com.spr.systemplacereservation.entity.dto.ReservationWithoutDateDTO;

public interface ReservationService {

    public Reservation makeReservation(ReservationDTO dto);

    public void deleteReservation(Integer id);

    public List<ReservationDTO> getReservationsAtGivenDate(LocalDate date);

    public Map<LocalDate, List<ReservationWithoutDateDTO>> getReserervationsAtGivenTimeSpan(LocalDate startingDate,
	    LocalDate endingDate);

}
