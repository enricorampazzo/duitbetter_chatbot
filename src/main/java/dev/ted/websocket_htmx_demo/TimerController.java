package dev.ted.websocket_htmx_demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TimerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TimerController.class);

    @PostMapping("/start-timer")
    public ResponseEntity<Void> startTimer() {
        LOGGER.info("Start Timer command received via POST");
        return ResponseEntity.status(200).build();
    }
}
