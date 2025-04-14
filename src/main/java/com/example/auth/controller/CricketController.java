package com.example.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.auth.beans.scope.LiveScoreRequest;
import com.example.auth.beans.scope.MatchSingleton;
import com.example.auth.beans.scope.UserPreferencesSession;

@RestController
@RequestMapping("/cricket")
public class CricketController {

	@Autowired
	private MatchSingleton matchSingleton; // Singleton scope	
	
	@Autowired
	private LiveScoreRequest liveScoreRequest; // Request scope
	
	@Autowired
	private UserPreferencesSession userPreferenceSession; // Session scope
	
	@GetMapping("/match-summary")
	public String getMatchSymmary() {
		return matchSingleton.getMatchDetails();
	}
	
	@GetMapping("/set-players")
	public String setPlayersInMatch() {
		matchSingleton.setPlayersInMatch();
		return "Success";
	}
	
}
