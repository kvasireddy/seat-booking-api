package com.seats.bookingapi.service;

import static org.mockito.ArgumentMatchers.any;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.seats.bookingapi.beans.DomesticFlight;
import com.seats.bookingapi.beans.InternationalFlight;
import com.seats.bookingapi.entity.Seat;
import com.seats.bookingapi.entity.Vehicle;
import com.seats.bookingapi.entity.Vehicle.VehicleType;
import com.seats.bookingapi.repository.VehiclesRepository;


@RunWith(MockitoJUnitRunner.class)
public class SeatBookingServiceTest {
	
	@Mock
	VehiclesRepository vehicleRepo;

	@InjectMocks
	SeatBookingService serviceInTest;

	@Test
	public void testCreateDomesticFlight() {
		String nameOfTheFlight = "Jet Airways";
		Mockito.when(vehicleRepo.saveAndFlush(any(Vehicle.class))).thenReturn(new DomesticFlight(nameOfTheFlight));
		Vehicle domesticVehicle = serviceInTest.createDomesticFlight(nameOfTheFlight);
		// Assert Creation of Vehicle Type.
		Assert.assertEquals("Type of the vehicle", VehicleType.DOMESTiC_FLIGHT.toString(),
				domesticVehicle.getType().toString());
		// Assert Number of seats
		Seat[][] seats = domesticVehicle.getSeats();
		Assert.assertEquals("Number of seats", 70, seats.length * seats[0].length);

		// Assert for Aisle Seats. Aisle Seat positions for domestic flight in row at
		// columns = 2, 3, 5, 6
		Assert.assertEquals("Aisle Seat Validation at 1st row position 2 ", true, seats[1][2].isAisleSeat());
		Assert.assertEquals("Aisle Seat Validation at 1st row position 3 ", true, seats[1][3].isAisleSeat());
		Assert.assertEquals("Aisle Seat Validation at 1st row position 5 ", true, seats[1][5].isAisleSeat());
		Assert.assertEquals("Aisle Seat Validation at 1st row position 6 ", true, seats[1][6].isAisleSeat());

		// Assert for Seat bookings. Expectation is to not create any bookings yet.
		// Booking should be null in the seat.

		Assert.assertNull("Seat Bookings should be null ", seats[1][2].getBooking());
	}
	
	@Test
	public void testCreateInternationalFlight() {
		String nameOfTheFlight = "Emirites Airways";
		Mockito.when(vehicleRepo.saveAndFlush(any(Vehicle.class))).thenReturn(new InternationalFlight(nameOfTheFlight));
		Vehicle internationalFlight = serviceInTest.createInternationFlight(nameOfTheFlight);
		// Assert Creation of Vehicle Type.
		Assert.assertEquals("Type of the vehicle", VehicleType.INTERNATIONAL_FLIGHT.toString(),
				internationalFlight.getType().toString());
		// Assert Number of seats
		Seat[][] seats = internationalFlight.getSeats();
		Assert.assertEquals("Number of seats", 200, seats.length * seats[0].length);

		// Assert for Aisle Seats. Aisle Seat positions for domestic flight in row at
		// columns = 3, 4, 7, 8)
		Assert.assertEquals("Aisle Seat Validation at 1st row position 2 ", true, seats[1][3].isAisleSeat());
		Assert.assertEquals("Aisle Seat Validation at 1st row position 3 ", true, seats[1][4].isAisleSeat());
		Assert.assertEquals("Aisle Seat Validation at 1st row position 5 ", true, seats[1][7].isAisleSeat());
		Assert.assertEquals("Aisle Seat Validation at 1st row position 6 ", true, seats[1][8].isAisleSeat());

		// Assert for Seat bookings. Expectation is to not create any bookings yet.
		// Booking should be null in the seat.

		Assert.assertNull("Seat Bookings should be null ", seats[1][2].getBooking());
	}
	
	@Test
	public void testCreateFlight() {
		String nameOfTheFlight = "Do not know Airways";
		Mockito.when(vehicleRepo.saveAndFlush(any(Vehicle.class))).thenReturn(new DomesticFlight(nameOfTheFlight));
		Vehicle domesticFlight = serviceInTest.createFlight(nameOfTheFlight, null);
		// Assert Creation of Vehicle Type.
		Assert.assertEquals("Type of the vehicle", VehicleType.DOMESTiC_FLIGHT.toString(),
				domesticFlight.getType().toString());
		
	}
	
	@Test
	public void testCreateFlightInternational() {
		String nameOfTheFlight = "Do not know International Airways";
		Mockito.when(vehicleRepo.saveAndFlush(any(Vehicle.class))).thenReturn(new InternationalFlight(nameOfTheFlight));
		Vehicle international = serviceInTest.createFlight(nameOfTheFlight, null);
		// Assert Creation of Vehicle Type.
		Assert.assertEquals("Type of the vehicle", VehicleType.INTERNATIONAL_FLIGHT.toString(),
				international.getType().toString());
		
	}

}
