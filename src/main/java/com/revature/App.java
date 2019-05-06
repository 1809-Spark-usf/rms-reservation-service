package com.revature;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.openfeign.EnableFeignClients;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * The Class App.
 * @author 1811-Java-Nick 12/27/18
 */
@EnableSwagger2
@SpringBootApplication
@EnableFeignClients
@EnableCircuitBreaker
public class App {
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
