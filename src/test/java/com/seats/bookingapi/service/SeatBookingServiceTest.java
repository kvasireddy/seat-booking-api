package com.seats.bookingapi.service;

import static org.mockito.ArgumentMatchers.any;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.seats.bookingapi.beans.Vehicle;
import com.seats.bookingapi.beans.VehicleType;
import com.seats.bookingapi.entity.DomesticFlight;
import com.seats.bookingapi.entity.Seat;
import com.seats.bookingapi.repository.DomesticVehiclesRepository;


@RunWith(MockitoJUnitRunner.class)
public class SeatBookingServiceTest {
	
	@Mock
	DomesticVehiclesRepository vehicleRepo;

	@InjectMocks
	SeatBookingService serviceInTest;

	@Test
	public void testCreateDomesticFlight() {
		String nameOfTheFlight = "Jet Airways";
		Mockito.when(vehicleRepo.saveAndFlush(any(DomesticFlight.class))).thenReturn(new DomesticFlight(nameOfTheFlight));
		Vehicle domesticVehicle = serviceInTest.createDomesticFlight(nameOfTheFlight);
		// Assert Creation of Vehicle Type.
		Assert.assertEquals("Type of the vehicle", VehicleType.DOMESTiC_FLIGHT.toString(),
				domesticVehicle.getVehicleType().toString());
		// Assert Number of seats
		List<Seat> seats = domesticVehicle.getSeats();
		Assert.assertEquals("Number of seats", 70, seats.size());
	}
	

}
