package com.example.auth.beans.scope;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class CitySingleton {
	
	public CitySingleton() {
		System.out.println("CitySingleton is created.");
	}
	 
	public Map<Integer,String> getCities(){
		Map<Integer,String> map = new HashMap<>();
		map.put(000002, "Mumbai");
		map.put(411021, "Pune");
		map.put(413207, "Amalner");		
		return map;
	}
	
}
