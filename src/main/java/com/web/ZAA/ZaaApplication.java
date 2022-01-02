package com.web.ZAA;

import com.web.ZAA.Core.Load;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ZaaApplication {

	public static void main(String[] args) {
		Load.loadInit();
		SpringApplication.run(ZaaApplication.class, args);
	}

}
