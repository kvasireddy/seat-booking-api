package com.seats.bookingapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.seats.bookingapi.entity.Vehicle;

public interface VehiclesRepository extends JpaRepository<Vehicle, Long>{

}
