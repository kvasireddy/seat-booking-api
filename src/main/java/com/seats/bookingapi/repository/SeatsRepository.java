package com.seats.bookingapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.seats.bookingapi.entity.Seat;

public interface SeatsRepository extends JpaRepository<Seat, Long>{

}
