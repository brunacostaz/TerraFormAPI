package br.com.fiap.terraform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TerraFormApplication {

    public static void main(String[] args) {
        SpringApplication.run(TerraFormApplication.class, args);
    }
}
