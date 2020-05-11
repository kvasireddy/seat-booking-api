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
		List<Seat> availableSeats = super.getAvailableSeats(flightId); //Sorted Collection of seats. 
		if(availableSeats.size() < numberOfSeats) {
			throw new Exception("No Seats to book. Available seats : "+ availableSeats.size());
		}
		if (numberOfSeats == 1) {
			foundSeats.add(availableSeats.get(0));
		}
		if (numberOfSeats > 1) {
			Vehicle flight = super.getVehicle(flightId);
			Seat[][] seatsArray = flight.getSeats();
			boolean found = false;
			if (numberOfSeats == 2) {
				for (int i = 0; i < seatsArray.length && !found; i++) {
					// Checking each row.
					List<Integer[]> consecutiveSeats = new ArrayList<>();
					consecutiveSeats.add(new Integer[] { 0, 1 });
					consecutiveSeats.add(new Integer[] { 5, 6 });

					for (Integer[] consseats : consecutiveSeats) {
						Seat seatA = seatsArray[i][consseats[0]];
						Seat seatB = seatsArray[i][consseats[1]];
						if (seatA.isSeatAvailableTobook() && seatB.isSeatAvailableTobook()) {
							found = found2Seats(foundSeats, seatA, seatB);
							break;
						}
					}

					Seat seatC = seatsArray[i][2];
					Seat seatD = seatsArray[i][3];
					Seat seatE = seatsArray[i][4];
					if (seatC.isSeatAvailableTobook() && seatD.isSeatAvailableTobook()) {
						found = found2Seats(foundSeats, seatC, seatD);
						break;
					}
					if (seatD.isSeatAvailableTobook() && seatE.isSeatAvailableTobook()) {
						found = found2Seats(foundSeats, seatD, seatE);
						break;
					}
					if (seatC.isSeatAvailableTobook() && seatE.isSeatAvailableTobook()) {
						found = found2Seats(foundSeats, seatC, seatE);
						break;
					}
				} 
			} else if(!found){
				Seat root = availableSeats.get(0);
				foundSeats.add(root);
				for(int i = 1; i< numberOfSeats; i++) {
					root = super.findNextShortestDistanceSeat(root, availableSeats);
					foundSeats.add(root);
				}
				
			}

		}

		return foundSeats;
	}


	private boolean found2Seats(List<Seat> foundSeats, Seat seatA, Seat seatB) {
		boolean found = true;
		seatA.setFound(found);
		foundSeats.add(seatA);
		seatB.setFound(found);
		foundSeats.add(seatB);
		return found;
	}
	
	public List<Seat> bookSeats(Long flightId, int numberOfSeats) throws Exception{
		List<Seat> seats = findSeats(flightId, numberOfSeats);
		User user = new User("Test", "dummy");
		Booking book = new Booking(user , numberOfSeats);
		seats.forEach(s -> s.setBooking(book));
		seatRepository.saveAll(seats);
		return seatRepository.saveAll(seats);
	}

}
