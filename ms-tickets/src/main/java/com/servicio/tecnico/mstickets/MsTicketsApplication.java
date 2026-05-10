package com.servicio.tecnico.mstickets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MsTicketsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsTicketsApplication.class, args);
    }

}
