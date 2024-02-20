package dev.ted.websocket_htmx_demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;

@RestController
public class HtmxGetTargetingController {

    @GetMapping("/replace-div")
    public String replaceDiv() {

        return """
                <div id="replaced-div">
                    Replaced DIV content from server, generated at %s...
                </div>"""
                .formatted(LocalTime.now());
    }

    @GetMapping("/replace-div-target-of-oob")
    public String replaceDivWithOobSwap() {
        return """
                <div id="target-of-oob" hx-swap-oob="true">
                    Replaced DIV content from server, generated at %s...
                </div>
                leftover text"""
                .formatted(LocalTime.now());
    }

    @GetMapping("/replace-div-target-of-oob-swap-none")
    public String replaceDivWithOobSwapAndSwapNoneOnClient() {
        return """
                <div id="target-of-oob-swap-none" hx-swap-oob="true">
                    Replaced DIV content from server, generated at %s...
                </div>
                ignored text"""
                .formatted(LocalTime.now());
    }

}
