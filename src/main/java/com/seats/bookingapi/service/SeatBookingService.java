package com.seats.bookingapi.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seats.bookingapi.beans.DomesticFlight;
import com.seats.bookingapi.beans.InternationalFlight;
import com.seats.bookingapi.entity.Seat;
import com.seats.bookingapi.entity.Vehicle;
import com.seats.bookingapi.entity.Vehicle.VehicleType;
import com.seats.bookingapi.repository.VehiclesRepository;

@Service
public class SeatBookingService {

	@Autowired
	private VehiclesRepository vehicleRepo;

	public Vehicle createDomesticFlight(String name) {
		return vehicleRepo.saveAndFlush(new DomesticFlight(name));
	}

	public Vehicle createInternationFlight(String name) {
		return vehicleRepo.saveAndFlush(new InternationalFlight(name));
	}

	/**
	 * Creates the flight of your choice. Default is Domestic Flight.
	 * 
	 * @param name
	 * @param type - Default is Domestic is no value found.
	 * @return
	 */
	public Vehicle createFlight(String name, VehicleType type) {
		if (type != null && type.equals(VehicleType.INTERNATIONAL_FLIGHT)) {
			return createInternationFlight(name);
		}
		return createDomesticFlight(name);
	}


	public List<Seat> getAvailableSeats(Long flightId) throws Exception {
		Vehicle vehicle = getVehicle(flightId);
		Seat[][] allSeatsIn2D = vehicle.getSeats();
		Set<Seat> allSeats = new HashSet<>();
		for (Seat[] arr : allSeatsIn2D) {
			allSeats.addAll(Arrays.asList(arr));
		}

		List<Seat> bookableSeats = allSeats.stream().filter(Seat::isSeatAvailableTobook).collect(Collectors.toList());
		Collections.sort(bookableSeats);
		return bookableSeats;
	}

	public Vehicle getVehicle(Long flightId) throws Exception {
		Optional<Vehicle> vehicle = vehicleRepo.findById(flightId);
		if (vehicle.isEmpty()) {
			throw new Exception("Please choose the right Vehicle ID");
		}
		return vehicle.get();
	}

	public Seat findNextShortestDistanceSeat(Seat root, List<Seat> seats) { //Send only bookable seats.  

		double minDistance = 1000;
		int row1 = root.getRow();
		int col1 = root.getColum();
		Seat closestSeat = null;

		for (Seat s : seats) {
			if (!s.isFound()) {
				int row2 = s.getRow();
				int col2 = s.getColum();
				double distance = Math.sqrt((row2 - row1) * (row2 - row1) + (col2 - col1) * (col2 - col1));
				if (distance == minDistance) {
					if (root.getRow() == s.getRow()) {
						closestSeat = s;
					}
				}
				if (distance < minDistance) {
					minDistance = distance;
					closestSeat = s;
				}
			}
		}

		return closestSeat;

	}

}
