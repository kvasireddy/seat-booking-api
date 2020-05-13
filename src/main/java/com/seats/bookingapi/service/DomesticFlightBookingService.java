package com.seats.bookingapi.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seats.bookingapi.beans.Vehicle;
import com.seats.bookingapi.entity.Booking;
import com.seats.bookingapi.entity.Seat;
import com.seats.bookingapi.entity.User;
import com.seats.bookingapi.repository.SeatsRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DomesticFlightBookingService extends SeatBookingService {

	@Autowired
	private SeatsRepository seatRepository;


	public Set<Seat> findSeats(Long flightId, int numberOfSeats) throws Exception {
		Set<Seat> foundSeats = new HashSet<>();
		Vehicle flight = super.getVehicle(flightId);
		List<Seat> availableSeats = super.getAvailableSeats(flightId); // Sorted Collection of seats.
		if (availableSeats.size() < numberOfSeats) {
			throw new Exception("No Seats to book. Available seats : " + availableSeats.size());
		}
		if (numberOfSeats == 1) {
			foundSeats.add(availableSeats.get(0));
		} else {
			boolean found = false;
			Seat root = availableSeats.get(0);
			Seat[][] seatsArray = new Seat[flight.getNumberOfRows()][flight.getNumberOfColumns()];
			for (Seat s : flight.getSeats())
				seatsArray[s.getSeatRow()][s.getSeatColumn()] = s;
			foundSeats = findAdjacentSeatsInEachRow(seatsArray, numberOfSeats, new HashSet<>());

			found = foundSeats.size() == numberOfSeats;

			log.info(found ? "Found adjacent {} seats. Return {}. "
					: "There are no adjacent {} seats to book. Hence proceeding with closest available seats {}", numberOfSeats, foundSeats);

			if (!found) {
				root = availableSeats.get(0);
				foundSeats.add(root);
				for (int i = 1; i < numberOfSeats; i++) {
					root = super.findClosestSeat(root, availableSeats);
					foundSeats.add(root);
				}
			}

		}
		return foundSeats;
	}

	public Set<Seat> findAdjacentSeatsInEachRow(Seat[][] seats, int numberOfSeats, Set<Seat> adjacentSeats) {

		List<Seat[]> seatRowList = new ArrayList<>();
		for (Seat[] seatRow : seats)
			seatRowList.add(seatRow);

		for (Seat[] seatRow : seatRowList) {
			Set<Seat> seatsFromRow = findInEachRow(seatRow,numberOfSeats);
			if(seatsFromRow.size() == numberOfSeats) {
				adjacentSeats = seatsFromRow;
				break;
			}
		}

		return adjacentSeats;

	}

	public Set<Seat> findInEachRow(Seat[] seatRow, int numberOfSeats) {
		Set<Seat> seatsAdjacent = new HashSet<Seat>();
		Seat root = seatRow[0];
		for (int j = 1; j < seatRow.length; j++) {
			log.debug("Seats in the current roww. {}", seatRow[j]);
			Seat seatToCheck = seatRow[j];
			if ((seatToCheck.isSeatAvailableTobook() && root.isAisleSeat() && seatToCheck.isAisleSeat())
					|| !seatToCheck.isSeatAvailableTobook() || !root.isSeatAvailableTobook()) {
				seatsAdjacent = new HashSet<>();
			} else {
				if (root.isSeatAvailableTobook() && seatToCheck.isSeatAvailableTobook() && (root.getSeatRow() == seatToCheck.getSeatRow())) {
					seatsAdjacent.add(root);
					seatsAdjacent.add(seatToCheck);
					if(numberOfSeats == seatsAdjacent.size())
						return seatsAdjacent;
				}
			}
			root = seatToCheck;
		}
		return seatsAdjacent;
	}

	

	public List<Seat> bookSeats(Long flightId, int numberOfSeats) throws Exception {
		Set<Seat> seats = findSeats(flightId, numberOfSeats);
		User user = new User("Test", "dummy");
		Booking book = new Booking(user, numberOfSeats);
		seats.forEach(s -> s.setBooking(book));
		seatRepository.saveAll(seats);
		return seatRepository.saveAll(seats);
	}

}
