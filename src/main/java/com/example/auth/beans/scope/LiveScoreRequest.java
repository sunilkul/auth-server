package com.example.auth.beans.scope;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value="request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LiveScoreRequest {

	private String userCountry;
	
	public void setUserCountry(String userCountry) {
		this.userCountry = userCountry;
	}
	
	public String getUserCountry() {
		return userCountry;
	}
}
