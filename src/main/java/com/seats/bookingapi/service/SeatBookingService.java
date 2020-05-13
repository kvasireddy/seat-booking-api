package com.seats.bookingapi.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seats.bookingapi.beans.Vehicle;
import com.seats.bookingapi.beans.VehicleType;
import com.seats.bookingapi.entity.DomesticFlight;
import com.seats.bookingapi.entity.Seat;
import com.seats.bookingapi.repository.DomesticVehiclesRepository;

@Service
public class SeatBookingService {

	@Autowired
	private DomesticVehiclesRepository domesticFlightRepository;
	
	
	

	public Vehicle createDomesticFlight(String name) {
		return domesticFlightRepository.save(new DomesticFlight(name));
	}


	/**
	 * Creates the flight of your choice. Default is Domestic Flight.
	 * 
	 * @param name
	 * @param type - Default is Domestic is no value found.
	 * @return
	 */
	public Vehicle createFlight(String name, VehicleType type) {
		/*
		 * if (type != null && type.equals(VehicleType.INTERNATIONAL_FLIGHT)) { return
		 * createInternationFlight(name); }
		 */
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
		Optional<DomesticFlight> vehicle = domesticFlightRepository.findById(flightId);
		if (vehicle.isEmpty()) {
			throw new Exception("Please choose the right Vehicle ID");
		}
		return (Vehicle) vehicle.get();
	}
	
	
	
	public Seat findClosestSeat(final Seat root, List<Seat> availableSeats) {
		availableSeats.remove(root);
		Comparator<Seat> byDistanceFromRoot = (Seat s1, Seat s2) -> {
			int comparedSeats = Double.compare(s1.getDistanceFromRoot(root), s2.getDistanceFromRoot(root));
			if(comparedSeats == 0) {
				if(root.isAisleSeat()) {
					if(s1.isAisleSeat()) {
						return -1;
					} if(s2.isAisleSeat()) {
						return 1;
					}
				} if(root.getSeatRow() == s1.getSeatRow()) {
					return -1;
				} if(root.getSeatRow() == s2.getSeatRow()) {
					return 1;
				}
			}
			return comparedSeats;
		};
		 Optional<Seat> mayNextSeat = availableSeats.stream().min(byDistanceFromRoot);
		return mayNextSeat.isPresent() ? mayNextSeat.get() : root;
	}

	
	public Set<Seat> findAdjacentSeats(Seat[][] seats, Seat root, int numberOfSeats, Set<Seat> adjacentSeats){
		if(adjacentSeats!= null && numberOfSeats == adjacentSeats.size()) {
			return adjacentSeats;
		}
		int row = root.getSeatRow();
		for(int i = row; row<seats.length; row++) {
			if(i == root.getSeatRow()) {
				for(int j = root.getSeatColumn()+1; j< seats[i].length; j++) {
					Seat seatToCheck = seats[i][j];
					if(seatToCheck.isSeatAvailableTobook() && root.isAisleSeat() && seatToCheck.isAisleSeat()) {
						findAdjacentSeats(seats, seatToCheck, numberOfSeats, new HashSet<>());
					} else {
						if(seatToCheck.isSeatAvailableTobook()) {
							adjacentSeats.add(root);
							adjacentSeats.add(seatToCheck);
						}
					}
				}
			} else {
				for (int j = 0; j< seats[i].length; j++) {
					if(seats[j][j].isSeatAvailableTobook()) {
						adjacentSeats = new HashSet<>();
						adjacentSeats.add(seats[i][j]);
						findAdjacentSeats(seats, seats[j][j], numberOfSeats, new HashSet<>());
					}
				}
				
			}
		}
		return adjacentSeats;
	}


}
