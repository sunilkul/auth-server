package com.example.auth.beans.scope;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserPreferencesSession {

	private String favoriteTeam;
	
	public String getFavoriteTeam() {
		return this.favoriteTeam;
	}
	
	public void setFavoriteTeam(String favoriteTeam) {
		this.favoriteTeam = favoriteTeam;
	}
}
