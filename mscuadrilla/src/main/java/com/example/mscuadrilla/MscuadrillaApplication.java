package com.example.mscuadrilla;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.mscuadrilla"})
public class MscuadrillaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MscuadrillaApplication.class, args);
	}

}
