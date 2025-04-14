package com.example.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration(proxyBeanMethods = false)
public class UserDbConfig {
	
	@Autowired
	CustomHikariConfig customHikariConfig;
	
	@Value("${spring.datasource.driver-class-name}")	
	private String driverClassName;
	
	@Value("${spring.datasource.username}")
	private String userName;
	
	@Value("${spring.datasource.password}")
	private String password;
	
	@Value("${spring.datasource.jdbcUrl}")
	private String jdbcUrl;
	
	
	@Bean	
	public HikariDataSource userDataSource() {	
		HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(jdbcUrl);
        hikariConfig.setUsername(userName);
        hikariConfig.setPassword(password);
        hikariConfig.setDriverClassName(driverClassName);
        hikariConfig.setPoolName("userdb-pool");
        hikariConfig.setMaximumPoolSize(customHikariConfig.getMaximumPoolSize());
        hikariConfig.setMinimumIdle(customHikariConfig.getMinimumIdle());
        hikariConfig.setIdleTimeout(customHikariConfig.getIdleTimeout());
        hikariConfig.setConnectionTimeout(customHikariConfig.getConnectionTimeout());
        hikariConfig.setMaxLifetime(customHikariConfig.getMaxLifetime());
        return new HikariDataSource(hikariConfig);		
	}

}
