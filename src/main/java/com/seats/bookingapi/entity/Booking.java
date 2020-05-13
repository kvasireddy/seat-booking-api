package com.seats.bookingapi.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
public class Booking {
	
	
	private @Id @GeneratedValue Long bookingId;
	
	@ManyToOne(cascade=CascadeType.ALL)
	private User user; 
	private int numberOfSeats;
	
	/*
	 * @OneToMany private List<Seat> seats;
	 */
	
	public Booking(User user, int numberOfSeats ) { //, List<Seat> seats) {
		//this.seats = seats;
		this.user = user;
		this.numberOfSeats = numberOfSeats;
	}
	

}
