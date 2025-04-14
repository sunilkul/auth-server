package com.example.auth.controller;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zaxxer.hikari.HikariDataSource;

@RestController
@RequestMapping("/hikari-info")
public class HikariPoolInfoController {
	
	@Autowired
	private HikariDataSource userDataSource;

	@GetMapping
	public String poolInfo() {

		
		return "HikariCP is configured with:\n" +
                "Maximum Pool Size: " + userDataSource.getMaximumPoolSize() + "\n" +
                "Minimum Idle Connections: " + userDataSource.getMinimumIdle() + "\n" +
                "Idle Timeout: " + userDataSource.getIdleTimeout() + "\n" +
                "Connection Timeout: " + userDataSource.getConnectionTimeout() + "\n" +
                "Pool Name: " + userDataSource.getPoolName();

	}
}
