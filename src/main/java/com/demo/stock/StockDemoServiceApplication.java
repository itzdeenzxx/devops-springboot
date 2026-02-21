package com.demo.stock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class StockDemoServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockDemoServiceApplication.class, args);
    }

    @GetMapping("/")
    public String hello() {
        return "Stock Demo Service (Java 11) is running 🚀";
    }
}
