package com.servicio.tecnico.eureraserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer // Para activar servidor
public class EureraServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EureraServerApplication.class, args);
    }

}
