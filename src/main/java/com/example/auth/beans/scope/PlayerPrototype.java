package com.example.auth.beans.scope;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class PlayerPrototype {
	
	@Autowired
	CitySingleton citySingleton;

	private String name;
	private int runs;

	public PlayerPrototype() {
		System.out.println("Player Prototype instance created.");
	}

	public String getDetails(Integer pinCode) {
		System.out.println("city name is "+ (citySingleton.getCities().getOrDefault(pinCode, "Not Found")));
		return "Player name :" + name + " - runs :" + runs;
	}

	public void setDetails(String name, int runs) {
		this.name = name;
		this.runs = runs;
	}
}
