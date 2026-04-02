package net.parksy.dbmulti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class DbmultiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DbmultiApplication.class, args);
	}

}
