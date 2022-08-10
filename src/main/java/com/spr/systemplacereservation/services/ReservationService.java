package com.spr.systemplacereservation.services;

import java.sql.Date;

import com.spr.systemplacereservation.entity.Reservation;
import com.spr.systemplacereservation.entity.dto.ReservationDTO;

public interface ReservationService {

	Reservation makeReservation(ReservationDTO dto);


}
