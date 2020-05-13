package com.seats.bookingapi.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

import com.seats.bookingapi.entity.Seat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@MappedSuperclass
public class Vehicle {
	
	private @Id @GeneratedValue Long vehicleId;

	private final VehicleType vehicleType;

	private final int numberOfRows;

	private final int numberOfColumns;

	@OneToMany(cascade=CascadeType.ALL)
	private final List<Seat> seats;


	public Vehicle(VehicleType type, int numberOfRows, int numberOfColumns, Set<Integer> aisleSeats) {
		this.numberOfColumns = numberOfColumns;
		this.numberOfRows = numberOfRows;
		this.vehicleType = type;
		//this.seats = new Seat[numberOfRows][numberOfColumns];
		this.seats = new ArrayList<Seat>();
		for (int i = 0; i < numberOfRows; i++) {
			for (int j = 0; j < numberOfColumns; j++) {
				this.seats.add(new Seat(i, j, aisleSeats.contains(Integer.valueOf(j)), null));
			}
		}
	}

}
