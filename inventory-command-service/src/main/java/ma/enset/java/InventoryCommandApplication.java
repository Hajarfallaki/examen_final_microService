package ma.enset.java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class InventoryCommandApplication {
    public static void main(String[] args) {
        SpringApplication.run(InventoryCommandApplication.class, args);
    }
}