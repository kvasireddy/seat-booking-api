package com.seats.bookingapi.entity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.seats.bookingapi.beans.Flight;
import com.seats.bookingapi.beans.VehicleType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "vehicle")
public class DomesticFlight extends Flight{
	
	private static final int numberOfRows = 10;
	private static final int numberOfColumns = 7;
	private static final Set<Integer> aisleSeat = new HashSet<>(Arrays.asList(1, 2, 4, 5));
	
	private static final String vehicleType = VehicleType.DOMESTiC_FLIGHT.toString();

	public DomesticFlight(String name) {
		super(VehicleType.valueOf(DomesticFlight.vehicleType), name, DomesticFlight.numberOfRows, DomesticFlight.numberOfColumns, DomesticFlight.aisleSeat);
	}
	
	public DomesticFlight() {
		super(VehicleType.DOMESTiC_FLIGHT, "Default", DomesticFlight.numberOfRows, DomesticFlight.numberOfColumns, DomesticFlight.aisleSeat);
	}

}
