package com.seats.bookingapi.beans;

import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class Flight extends Vehicle{
	
	private final String flightName;
	
	public Flight(VehicleType type, String name, int numberOfRows, int numberOfColumns, Set<Integer> aisleSeats) {
		super(type, numberOfRows, numberOfColumns, aisleSeats);
		this.flightName = name;
	}
	

}
