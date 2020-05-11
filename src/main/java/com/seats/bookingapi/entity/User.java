package com.seats.bookingapi.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class User {
	
	private @Id @GeneratedValue Long id;
	private String firstName;
	private String lastName;
	
	public User(String firstName, String LastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

}
