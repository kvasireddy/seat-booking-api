package com.seats.bookingapi.entity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.seats.bookingapi.beans.Flight;
import com.seats.bookingapi.beans.VehicleType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
//@Entity
//@Table(name = "vehicle")
public class InternationalFlight extends Flight{
	
	private static final int numberOfRows = 20;
	private static final int numberOfColumns = 10;
	
	private static final Set<Integer> aisleSeat = new HashSet<>(Arrays.asList(2, 3, 6, 7));
	
	private static final VehicleType vehicleType = VehicleType.INTERNATIONAL_FLIGHT;

	public InternationalFlight(String name) {
		super(InternationalFlight.vehicleType, name, InternationalFlight.numberOfRows, InternationalFlight.numberOfColumns, InternationalFlight.aisleSeat);
	}
	

}
