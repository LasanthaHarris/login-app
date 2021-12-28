package edu.home.and.company.apps.login.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class LoginAppDiscoveryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoginAppDiscoveryServiceApplication.class, args);
	}

}
