package net.nemisolv.lib;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;




// because this is a library, we don't need to run it as a standalone application
@SpringBootApplication
public class CommonLibApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommonLibApplication.class, args);
	}

}
