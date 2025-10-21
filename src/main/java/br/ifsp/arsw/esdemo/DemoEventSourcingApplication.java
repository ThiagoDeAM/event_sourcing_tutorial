package br.ifsp.arsw.esdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "br.ifsp.arsw.esdemo")
public class DemoEventSourcingApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoEventSourcingApplication.class, args);
    }
}
