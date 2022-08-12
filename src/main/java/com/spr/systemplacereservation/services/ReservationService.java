package com.spr.systemplacereservation.services;

import java.time.LocalDate;
import java.util.List;

import com.spr.systemplacereservation.entity.Reservation;
import com.spr.systemplacereservation.entity.dto.ReservationDTO;

public interface ReservationService {

    public Reservation makeReservation(ReservationDTO dto);

    public void deleteReservation(Integer id);

    public List<ReservationDTO> getReservationsAtGivenDate(LocalDate date);

}
