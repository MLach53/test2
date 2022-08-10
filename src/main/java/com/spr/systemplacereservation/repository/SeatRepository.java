package com.spr.systemplacereservation.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.spr.systemplacereservation.model.Seat;

@Repository
public interface SeatRepository extends CrudRepository<Seat, Integer> {

}
