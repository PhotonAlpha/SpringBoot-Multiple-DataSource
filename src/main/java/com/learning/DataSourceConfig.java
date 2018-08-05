package com.learning;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DataSourceConfig {
	@Autowired
    private Environment env;
	
	//reference https://docs.spring.io/spring-boot/docs/current/reference/html/howto-data-access.html
	@Bean
	@Primary
	@ConfigurationProperties("spring.primary.datasource")
	public DataSourceProperties firstDataSourceProperties() {
		return new DataSourceProperties();
	}
	@Bean
	@ConfigurationProperties("spring.secondary.datasource")
	public DataSourceProperties secondDataSourceProperties() {
		return new DataSourceProperties();
	}
	
	@Bean(name = "primaryDS")
	@Qualifier("primaryDS")
	@Primary
	public DataSource primaryDataSource(){
		return firstDataSourceProperties().initializeDataSourceBuilder().build();
//		return DataSourceBuilder.create().build();
//	    DriverManagerDataSource dataSource = new DriverManagerDataSource();
//	    dataSource.setDriverClassName(env.getProperty("spring.primary.datasource.driver-class-name"));
//	    dataSource.setUrl(env.getProperty("spring.primary.datasource.url"));
//	    dataSource.setUsername(env.getProperty("spring.primary.datasource.username"));
//	    dataSource.setPassword(env.getProperty("spring.primary.datasource.password"));
//
//	    return dataSource;
	}
 
	@Bean(name = "secondaryDS")
	@Qualifier("secondaryDS")
//	@ConfigurationProperties(prefix="spring.secondary.datasource")
	public DataSource secondaryDataSource(){
		return secondDataSourceProperties().initializeDataSourceBuilder().build();
//		DriverManagerDataSource dataSource = new DriverManagerDataSource();
//	    dataSource.setDriverClassName(env.getProperty("spring.secondary.datasource.driver-class-name"));
//	    dataSource.setUrl(env.getProperty("spring.secondary.datasource.url"));
//	    dataSource.setUsername(env.getProperty("spring.secondary.datasource.username"));
//	    dataSource.setPassword(env.getProperty("spring.secondary.datasource.password"));
//
//	    return dataSource;
	}

}
