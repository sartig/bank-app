package com.fdmgroup.CreditCardProject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CreditCardProjectApplication {

	static final Logger log = LogManager.getLogger(CreditCardProjectApplication.class);	
	
	public static void main(String[] args) {
		System.setProperty("log4j.configurationFile","classpath:log4j2.xml");
		log.info("Booting Up OrderWebsite Application...");
		SpringApplication.run(CreditCardProjectApplication.class, args);
	}

}
