package com.seats.bookingapi.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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
import com.sun.rowset.internal.Row;

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
		List<Seat> allSeats = vehicle.getSeats();
		List<Seat> bookableSeats = allSeats.stream().filter( s -> s.isSeatAvailableTobook() && !s.isLocked()).collect(Collectors.toList());
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
	
	
	
	public Seat findClosestSeat(final Seat root, List<Seat> availableSeats) {
		Comparator<Seat> byDistanceFromRoot = (Seat s1, Seat s2) -> {
			int comparedSeats = Double.compare(s1.getDistanceFromRoot(root), s2.getDistanceFromRoot(root));
			if(comparedSeats == 0) {
				if(root.isAisleSeat()) {
					if(s1.isAisleSeat()) {
						return 1;
					} if(s2.isAisleSeat()) {
						return -1;
					}
				} if(root.getRow() == s1.getRow()) {
					return -1;
				} if(root.getRow() == s2.getRow()) {
					return 1;
				}
			}
			return comparedSeats;
		};
		 Optional<Seat> mayNextSeat = availableSeats.stream().min(byDistanceFromRoot);
		return mayNextSeat.isPresent() ? mayNextSeat.get() : root;
	}

	
	/**
	 * This method is deprecated. Please Use findClosestSeat(...
	 * @param root
	 * @param seats
	 * @return
	 */
	@Deprecated
	public Seat findNextShortestDistanceSeat(Seat root, List<Seat> seats) { //Send only bookable seats.  

		double minDistance = 1000;
		int row1 = root.getRow();
		int col1 = root.getColum();
		Seat closestSeat = null;

		for (Seat s : seats) {
			if (!s.isLocked()) {
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
