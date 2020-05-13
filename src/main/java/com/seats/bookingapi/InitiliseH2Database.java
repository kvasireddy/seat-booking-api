package com.seats.bookingapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.seats.bookingapi.entity.DomesticFlight;
import com.seats.bookingapi.repository.DomesticVehiclesRepository;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class InitiliseH2Database {

	@Bean 
	public CommandLineRunner initFootballTeams(DomesticVehiclesRepository vehicleRepository) {
		return args -> {
			log.info("Loading repository " + vehicleRepository.save(new DomesticFlight("Dummy")));
		};	
	}

}
