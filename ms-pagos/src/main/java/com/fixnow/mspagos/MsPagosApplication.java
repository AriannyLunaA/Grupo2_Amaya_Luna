package com.fixnow.mspagos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients // <-- ¡Súper importante para que funcione la comunicación con ms-tickets!
public class MsPagosApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsPagosApplication.class, args);
    }

}
