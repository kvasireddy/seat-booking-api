package com.seats.bookingapi.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Slf4j
public class Seat implements Comparable<Seat> {

	@PrimaryKeyJoinColumn
	private @Id @GeneratedValue Long seatId;
	private Integer seatRow;
	private Integer seatColumn;
	private boolean aisleSeat;

	private boolean locked;

	private double distanceFromRoot;

	@ManyToOne(cascade=CascadeType.ALL)
	//@JoinColumn(name = "bookingId", nullable = true)
	// If value is null, then the seat is available to book
	private Booking booking;


	public Seat(Integer row, Integer colum, boolean isAilseSeat, Booking booking) {
		log.debug("Creating seat at {} row and {} Column. Seat is Aisle? {}", row, colum, isAilseSeat);
		this.seatRow = row;
		this.seatColumn = colum;
		this.aisleSeat = isAilseSeat;
		this.booking = booking;
		//this.distanceFromRoot = Math.sqrt(row* row + colum * colum);
	}
	
	public double getDistanceFromRoot(Seat seat) {
		return Math.sqrt((this.seatRow - seat.seatRow)* (this.seatRow - seat.seatRow) + (this.seatColumn - seat.seatColumn) * (this.seatColumn - seat.seatColumn) );
	}

	@Override
	public int compareTo(Seat o) {
		final int ROW_BEFORE = -1;
		final int ROW_EQUAL = 0;
		final int ROW_AFTER = 1;
		if (this.seatRow < o.seatRow)
			return ROW_BEFORE;
		if (this.seatRow > o.seatRow)
			return ROW_AFTER;
		if (this.seatRow == o.seatRow) {
			return this.seatColumn.compareTo(o.seatColumn);
		}
		return ROW_EQUAL;
	}

	public boolean isSeatAvailableTobook() {
		return this.booking == null;
	}

}
