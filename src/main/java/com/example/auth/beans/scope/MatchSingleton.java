package com.example.auth.beans.scope;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class MatchSingleton {
	
	//@Autowired
	//private PlayerPrototype playerProtoType; // Prototype scope
	
	@Lookup
	public PlayerPrototype getPlayerPrototype() {return null;}
	
	private int matchId = 1001;
	private String teams = "IND vs AUS";

	public MatchSingleton() {
		System.out.println("Match Instance Craeted.");
	}

	public String getMatchDetails() {
		return "Match [matchId=" + matchId + ", teams=" + teams + "]";
	}
	
	public void setPlayersInMatch() {
		
		Map<String,Integer> playerMap = new HashMap<>();
		playerMap.put("Sunil", 411021);
		playerMap.put("Abhishek", 411021);
		playerMap.put("Rishi", 170);
		playerMap.put("Rupesh", 300);
		playerMap.put("Aashu", 413207);
		
		
		playerMap.entrySet().forEach(x -> {
			PlayerPrototype  playerProtoType = getPlayerPrototype();
			playerProtoType.setDetails(x.getKey(), x.getValue());
		System.out.println("hashCode of prptotype : "+ playerProtoType.hashCode());
		System.out.println("player details : "+ playerProtoType.getDetails(x.getValue()));
		});
		
		
	} 

}
