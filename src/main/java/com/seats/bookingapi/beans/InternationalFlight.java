package com.seats.bookingapi.beans;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class InternationalFlight extends Flight{
	
	private static final int numberOfRows = 20;
	private static final int numberOfColumns = 10;
	
	private static final Set<Integer> aisleSeat = new HashSet<>(Arrays.asList(3, 4, 7, 8));
	
	public InternationalFlight(String name) {
		super(VehicleType.INTERNATIONAL_FLIGHT, name, InternationalFlight.numberOfRows, InternationalFlight.numberOfColumns, InternationalFlight.aisleSeat);
	}
	

}
