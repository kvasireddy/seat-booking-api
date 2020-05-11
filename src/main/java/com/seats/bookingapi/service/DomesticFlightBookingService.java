package com.seats.bookingapi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seats.bookingapi.entity.Booking;
import com.seats.bookingapi.entity.Seat;
import com.seats.bookingapi.entity.User;
import com.seats.bookingapi.entity.Vehicle;
import com.seats.bookingapi.repository.SeatsRepository;

@Service
public class DomesticFlightBookingService extends SeatBookingService {

	@Autowired
	private SeatsRepository seatRepository;

	public List<Seat> findSeats(Long flightId, int numberOfSeats) throws Exception {
		List<Seat> foundSeats = new ArrayList<>();
		List<Seat> availableSeats = super.getAvailableSeats(flightId); // Sorted Collection of seats.
		if (availableSeats.size() < numberOfSeats) {
			throw new Exception("No Seats to book. Available seats : " + availableSeats.size());
		}
		if (numberOfSeats == 1) {
			foundSeats.add(availableSeats.get(0));
		}
		if (numberOfSeats > 1) {
			Seat root = availableSeats.get(0);
			foundSeats.add(root);
			for (int i = 1; i < numberOfSeats; i++) {
				root = super.findClosestSeat(root, availableSeats);
				foundSeats.add(root);
			}

		}

		return foundSeats;
	}

	public List<Seat> bookSeats(Long flightId, int numberOfSeats) throws Exception {
		List<Seat> seats = findSeats(flightId, numberOfSeats);
		User user = new User("Test", "dummy");
		Booking book = new Booking(user, numberOfSeats);
		seats.forEach(s -> s.setBooking(book));
		seatRepository.saveAll(seats);
		return seatRepository.saveAll(seats);
	}

}
