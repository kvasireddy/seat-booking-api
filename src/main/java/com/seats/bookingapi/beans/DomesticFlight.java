package com.seats.bookingapi.beans;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DomesticFlight extends Flight{
	
	private static final int numberOfRows = 10;
	private static final int numberOfColumns = 7;
	private static final Set<Integer> aisleSeat = new HashSet<>(Arrays.asList(2, 3, 5, 6));

	public DomesticFlight(String name) {
		super(VehicleType.DOMESTiC_FLIGHT, name, DomesticFlight.numberOfRows, DomesticFlight.numberOfColumns, DomesticFlight.aisleSeat);
	}

}
