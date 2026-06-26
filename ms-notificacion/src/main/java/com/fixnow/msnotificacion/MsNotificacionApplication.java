package com.fixnow.msnotificacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsNotificacionApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsNotificacionApplication.class, args);
    }

}
