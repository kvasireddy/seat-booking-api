package com.seats.bookingapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.seats.bookingapi.entity.DomesticFlight;

public interface DomesticVehiclesRepository extends JpaRepository<DomesticFlight, Long>{

	//List<Vehicle> findAllByVehicleType(VehicleType vehicleType);
	
}
