package com.empresa.pos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Hooks;

@SpringBootApplication(scanBasePackages = {"com.empresa"})
public class PosApplication {
    public static void main(String[] args) {
        Hooks.enableAutomaticContextPropagation();
        Hooks.enableContextLossTracking();
        SpringApplication.run(PosApplication.class, args);
    }
}