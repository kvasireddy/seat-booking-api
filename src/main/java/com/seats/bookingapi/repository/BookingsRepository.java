package com.seats.bookingapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.seats.bookingapi.entity.Booking;

public interface BookingsRepository extends JpaRepository<Booking, Long>{

}
