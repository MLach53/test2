package com.spr.systemplacereservation.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.spr.systemplacereservation.model.Reservation;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Integer> {

}
