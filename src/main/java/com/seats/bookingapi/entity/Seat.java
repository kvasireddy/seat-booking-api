package com.seats.bookingapi.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
public class Seat implements Comparable<Seat> {

	private @Id @GeneratedValue Long id;
	private Integer row;
	private Integer colum;
	private boolean aisleSeat;

	private boolean found;

	private double distanceFromRoot;

	@ManyToOne
	@JoinColumn(name = "bookingId", nullable = true) // If value is null, then the seat is available to book
	private Booking booking;

	@ManyToOne

	private Vehicle vehicle;

	public Seat(Integer row, Integer colum, boolean isAilseSeat, Booking booking, Vehicle vehicle) {
		this.row = row;
		this.colum = colum;
		this.aisleSeat = isAilseSeat;
		this.booking = booking;
		this.vehicle = vehicle;
		this.distanceFromRoot = Math.sqrt(row* row + colum * colum);
	}

	@Override
	public int compareTo(Seat o) {
		final int ROW_BEFORE = -1;
		final int ROW_EQUAL = 0;
		final int ROW_AFTER = 1;
		if (this.row < o.row)
			return ROW_BEFORE;
		if (this.row > o.row)
			return ROW_AFTER;
		if (this.row == o.row) {
			return this.colum.compareTo(o.colum);
		}
		return ROW_EQUAL;
	}

	public boolean isSeatAvailableTobook() {
		return this.booking == null;
	}

}
