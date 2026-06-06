package com.isees;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
    exclude = {
        org.springframework.boot.autoconfigure.http.client.HttpClientAutoConfiguration.class,
        org.springframework.boot.autoconfigure.web.client.RestClientAutoConfiguration.class
    }
)
public class AddmissionWebsiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(AddmissionWebsiteApplication.class, args);
    }
}
