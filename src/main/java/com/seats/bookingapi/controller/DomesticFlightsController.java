package com.seats.bookingapi.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seats.bookingapi.beans.Vehicle;
import com.seats.bookingapi.entity.DomesticFlight;
import com.seats.bookingapi.entity.Seat;
import com.seats.bookingapi.repository.DomesticVehiclesRepository;
import com.seats.bookingapi.service.DomesticFlightBookingService;

@RestController
@RequestMapping(value = "/flights/domestic", path = "/flights/domestic") 
public class DomesticFlightsController {

	@Autowired
	private DomesticVehiclesRepository vehicleRepository;

	@Autowired
	private DomesticFlightBookingService domesticFlightService;

	@GetMapping(path = "/")
	public List<DomesticFlight> findAllDomesticVehicles() {
		return vehicleRepository.findAll();
	}

	@GetMapping(path = "/{flightId}")
	public Vehicle findDomesticVehicles(@PathVariable("flightId") Long flightId) {
		Optional<DomesticFlight> vehicleMayBe = vehicleRepository.findById(flightId);
		return vehicleMayBe.isPresent() ? (Vehicle) vehicleMayBe.get() : null;
	}

	@GetMapping(path = "/{flightId}/availableSeats")
	public List<Seat> findSeatsToBook(@PathVariable("flightId") Long flightId) {
		try {
			return domesticFlightService.getAvailableSeats(flightId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@PostMapping(path = "/{flightId}/book")
	public List<Seat> bookSeats(@PathVariable("flightId") Long flightId,
			@RequestParam(value = "numberOfSeats", required = true) int numberOfSeats) {
		try {
			List<Seat> seatsBooked = domesticFlightService.bookSeats(flightId, numberOfSeats);
			Collections.sort(seatsBooked);
			return seatsBooked;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null; 
	}

}
