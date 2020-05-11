package com.seats.bookingapi.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public abstract class Vehicle {

	public enum VehicleType {
		DOMESTiC_FLIGHT, INTERNATIONAL_FLIGHT;
	}

	private @Id @GeneratedValue Long id;

	private final VehicleType type;

	private final int numberOfRows;

	private final int numberOfColumns;

	@OneToMany
	private final List<Seat> seats;

	private final Set<Integer> aisleSeats;

	public Vehicle(VehicleType type, int numberOfRows, int numberOfColumns, Set<Integer> aisleSeats) {
		this.numberOfColumns = numberOfColumns;
		this.numberOfRows = numberOfColumns;
		this.type = type;
		//this.seats = new Seat[numberOfRows][numberOfColumns];
		this.seats = new ArrayList<Seat>();
		for (int i = 0; i < numberOfRows; i++) {
			for (int j = 0; j < numberOfColumns; j++) {
				this.seats.add(new Seat(i, j, aisleSeats.contains(Integer.valueOf(j)), null));
			}
		}
		this.aisleSeats = aisleSeats;
	}

}
