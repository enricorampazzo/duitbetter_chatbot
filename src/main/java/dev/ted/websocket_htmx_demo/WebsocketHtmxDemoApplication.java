package dev.ted.websocket_htmx_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WebsocketHtmxDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebsocketHtmxDemoApplication.class, args);
    }

}
